package com.example.omisetest.presentation

import android.app.Activity
import com.example.omisetest.Interactor

abstract class EventHandlerAction(context: Activity) : Interactor(context) {

  abstract fun execute()
}
