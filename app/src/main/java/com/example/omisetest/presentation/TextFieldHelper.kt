package com.example.omisetest.presentation

import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.example.omisetest.R


var TextInputLayout.text: String
  get() = editText?.text.toString()
  set(value) {
    editText?.value = value
  }

var TextInputLayout.helperText: String?
  get() = error.toString()
  set(value) {
    val style = when (value) {
      null -> R.style.AppTheme_TextErrorAppearance
      else -> R.style.AppTheme_TextHelperAppearance
    }
    setErrorTextAppearance(style)
    error = value
  }

fun TextInputLayout.clear() {
  helperText = null
  error = null
  editText?.clear()
}

fun TextInputLayout.onDone(onDone: () -> Unit) {
  editText?.onDone(onDone)
}

fun TextInputLayout.onExit(onExit: () -> Unit) {
  editText?.onExit(onExit)
}

var EditText.value: String
  get() = text.toString()
  set(value) {
    setText(value, TextView.BufferType.EDITABLE)
  }

fun EditText.clear() {
  error = null
  text?.clear()
}

fun EditText.onDone(onDone: () -> Unit) {
  setOnEditorActionListener(doneHandler(onDone))
}

private fun doneHandler(onDone: () -> Unit) = TextView.OnEditorActionListener { _, actionId, _ ->
  when (actionId) {
    EditorInfo.IME_ACTION_DONE -> {
      onDone()
      true
    }
    EditorInfo.IME_ACTION_NEXT -> {
      onDone()
      true
    }
    else -> false
  }
}

fun View.onExit(onExit: () -> Unit) {
  onFocusChangeListener = exitHandler(onExit)
}

private fun exitHandler(onExit: () -> Unit) = View.OnFocusChangeListener { _, hasFocus ->
  if (!hasFocus) { onExit() }
}
