package com.example.omisetest.presentation

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import com.example.omisetest.Interactor

abstract class RefreshableInteractor(
  context: Activity,
  private val refreshContainer: SwipeRefreshLayout
) : Interactor(context) {

  protected fun refresh(func: () -> Unit) = bg {
    ui { refreshContainer.isRefreshing = true }
    func()
    ui { refreshContainer.isRefreshing = false }
  }
}
