package com.example.omisetest.presentation

import android.databinding.BindingAdapter
import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.Glide

open class CharityViewModel(
  open val name: String,
  open val logoUrl: String,
  open val id: String
) {

  companion object {

    @JvmStatic
    @BindingAdapter("bind:logoUrl")
    fun loadImage(view: AppCompatImageView, logoUrl: String) { // This methods should not have any return type, = declaration would make it return that object declaration.
      Glide.with(view.context).load(logoUrl).into(view)
    }
  }
}
