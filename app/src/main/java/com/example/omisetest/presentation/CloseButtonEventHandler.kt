package com.example.omisetest.presentation

import android.app.Activity

class CloseButtonEventHandler(context: Activity) : EventHandlerAction(context) {

  override fun execute() { finishActivity()  }
}
