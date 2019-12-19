package com.example.omisetest.presentation

import android.os.Bundle
import com.example.omisetest.R

abstract class ClosableActivity : AppBarActivity() {

  private lateinit var action: EventHandlerAction

  open fun buildCloseAction(): EventHandlerAction = CloseButtonEventHandler(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    action = buildCloseAction()
  }

  override fun onDismiss() {
    action.execute()
  }
}
