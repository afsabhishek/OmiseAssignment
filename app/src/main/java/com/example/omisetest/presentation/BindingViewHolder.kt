package com.example.omisetest.presentation

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.example.omisetest.BR

typealias OnClick<T> = ((T) -> Unit)?

class BindingViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: T, onClick: OnClick<T>) {
    val viewModel = when (item) {
      is ListItem<*> -> item.viewModel
      else -> item
    }
    binding.setVariable(BR.viewModel, viewModel)
    binding.executePendingBindings()

    if (onClick != null) {
      itemView.setOnClickListener { onClick(item) }
    }
  }
}
