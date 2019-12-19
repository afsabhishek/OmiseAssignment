package com.example.omisetest.presentation

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup

interface ViewHolderBinder<T> {

  fun buildViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
    return BindingViewHolder(binding)
  }

  fun bindViewHolder(holder: BindingViewHolder<T>, position: Int, onClick: OnClick<T>) {
    holder.bind(getItemForPosition(position), onClick)
  }

  fun getItemForPosition(position: Int): T
}
