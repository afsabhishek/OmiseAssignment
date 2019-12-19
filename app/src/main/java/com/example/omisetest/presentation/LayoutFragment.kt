package com.example.omisetest.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class LayoutFragment : Fragment() {

  abstract val layoutId: Int

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(layoutId, container, false)

  protected fun enableLinksIn(vararg links: AppCompatTextView) {
    links.forEach {
      it.movementMethod = LinkMovementMethod.getInstance()
    }
  }
}
