package com.example.omisetest.presentation

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.omisetest.R
import com.example.omisetest.presentation.charity.presentation.CharityViewModel
import kotlinx.android.synthetic.main.view_charity.view.*

abstract class BindingRecyclerAdapter<T>(
  private val onClick: OnClick<T>
) : RecyclerView.Adapter<BindingViewHolder<T>>(), ViewHolderBinder<T> {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> = buildViewHolder(parent, viewType)
  override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
    bindViewHolder(holder, position, onClick)
    Glide.with(holder.itemView.context).load((getItemForPosition(position) as CharityViewModel.CharityDetails).logoUrl).into(holder.itemView.findViewById<AppCompatImageView>(R.id.logoUrl))
  }
}

  class RecyclerViewListAdapter<T : Any>(
    private val itemLayoutId: Int,
    onClick: OnClick<T> = null,
    private var _list: List<T> = emptyList()
  ) : BindingRecyclerAdapter<T>(onClick) {

    override fun getItemViewType(position: Int) = itemLayoutId

    override fun getItemForPosition(position: Int): T = _list[position]
    override fun getItemCount() = _list.size

    var list: List<T>
      get() = _list
      set(value) {
        _list = value
        notifyDataSetChanged()
      }
  }
