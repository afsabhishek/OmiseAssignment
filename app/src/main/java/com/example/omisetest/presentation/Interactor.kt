package com.example.omisetest

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.reflect.KClass

abstract class Interactor(protected val context: Activity) {

  protected fun ui(func: () -> Unit) {
    context.runOnUiThread(func)
  }

  fun bg(func: () -> Unit) {
    AsyncTask.execute(func)
  }

  protected fun recreate(intent: Intent) {
    context.startActivity(intent)
    context.finish()
    context.overridePendingTransition(0, 0)
  }

  private fun launch(intent: Intent) {
    if (context.isFinishing) return
    if (context != null && intent.resolveActivity(context.packageManager) != null) {
      context.startActivity(intent)
    }
  }

  protected fun launch(url: String) {
    launch(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  }

  protected fun <T : Activity> topTo(activity: KClass<T>, text: String, withFlags: Boolean) = bg {
    launch(Intent(context, activity.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
      putExtra(text, withFlags)
    })
    context.overridePendingTransition(0, 0)
  }

  protected fun <T : Activity> launch(activity: KClass<T>, text: String) = bg {
    launch(Intent(context, activity.java).apply {
      putExtra("object", text)
    })
    context.overridePendingTransition(0, 0)
  }

  protected fun <T : Activity> launch(activity: KClass<T>, withFlags: (Intent.() -> Unit) = {}) = bg {
    launch(Intent(context, activity.java).apply(withFlags))
    context.overridePendingTransition(0, 0)
  }

  protected fun <T : Activity> popTo(activity: KClass<T>) = launch(activity) {
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
  }

  protected fun <T : Activity> clearTo(activity: KClass<T>) = launch(activity) {
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
  }

  protected fun finishActivity() = context.finish()

  data class DialogButtonAction(
    val stringId: Int = R.string.action_ok,
    val action: () -> Unit = {}
  )

  protected fun errorDialogStyled(
    message: String,
    dismissAction: DialogButtonAction = DialogButtonAction(),
    extraAction: DialogButtonAction? = null
  ) {
    val dialog = AlertDialog.Builder(context).apply {
      setMessage(message)
      setIcon(R.drawable.ic_exclamation_mark)
      setTitle(R.string.error_title)
      dismissAction.run {
        setPositiveButton(stringId) { _, _ -> action() }
      }
      extraAction?.run {
        setNeutralButton(stringId) { _, _ -> action() }
      }
    }.create()

    if (!context.isFinishing) {
      dialog.apply {
        show()
        listOf(DialogInterface.BUTTON_POSITIVE, DialogInterface.BUTTON_NEUTRAL).forEach {
          getButton(it).setTextColor(getColor(R.color.brand_darkBlue))
        }
      }
    }
  }

  protected fun alertDialogStyled(
    message: String,
    dismissAction: DialogButtonAction = DialogButtonAction(),
    extraAction: DialogButtonAction? = null
  ) {
    val dialog = AlertDialog.Builder(context).apply {
      setMessage(message)
      setIcon(R.drawable.ic_exclamation_mark)
      dismissAction.run {
        setPositiveButton(stringId) { _, _ -> action() }
      }
      extraAction?.run {
        setNeutralButton(stringId) { _, _ -> action() }
      }
    }.create()

    if (!context.isFinishing) {
      dialog.apply {
        show()
        listOf(DialogInterface.BUTTON_POSITIVE, DialogInterface.BUTTON_NEUTRAL).forEach {
          getButton(it).setTextColor(getColor(R.color.brand_darkBlue))
        }
      }
    }
  }

  protected fun showBffError(response: Response<*>) = ui {
    val buttonText = R.string.error_action_tryAgain
    when (response) {
      is Response.Error -> errorDialogStyled(response.message, DialogButtonAction(buttonText))
      is Response.Failure -> errorDialogStyled(response.message, DialogButtonAction(buttonText))
    }
  }

  protected fun onClick(view: View, func: () -> Unit) = View.OnClickListener { func() }.onClick(view)

  fun hideSoftInput(requestingView: View) {
    with(context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
      hideSoftInputFromWindow(requestingView.windowToken, 0)
    }
  }

  protected val getString: (Int) -> String = { context.getString(it) }
  protected val getColor: (Int) -> Int = { ContextCompat.getColor(context, it) }

  protected fun store(key: PrefsKey, value: String) {
    prefs.edit().apply {
      putString(key.value, value)
      apply()
    }
  }

  protected fun retrieve(key: PrefsKey): String? = prefs.getString(key.value, null)

  private val prefs
    get() = context.getSharedPreferences(storeName, Context.MODE_PRIVATE)

  val storeBoolTrue = "true"
  val storeBoolFalse = "false"
}

const val storeName = "omise_prefs"

enum class PrefsKey(val value: String)
