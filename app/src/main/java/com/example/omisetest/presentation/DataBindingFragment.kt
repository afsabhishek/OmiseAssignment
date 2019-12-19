package com.example.omisetest.presentation

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class DataBindingFragment<B : ViewDataBinding> : LayoutFragment() {

  protected lateinit var binding: B

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    return binding.root
  }
}
