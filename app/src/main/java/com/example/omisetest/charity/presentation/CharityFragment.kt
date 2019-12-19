package com.example.omisetest.charity.presentation

import android.os.Bundle
import android.view.View
import com.example.omisetest.R
import com.example.omisetest.databinding.FragmentCharityBinding
import com.example.omisetest.presentation.DataBindingFragment
import com.example.omisetest.presentation.RecyclerViewListAdapter
import com.example.omisetest.presentation.charity.presentation.CharityViewModel
import kotlinx.android.synthetic.main.fragment_charity.*


class CharityFragment : DataBindingFragment<FragmentCharityBinding>() {

  override val layoutId = R.layout.fragment_charity
  private val itemLayout = R.layout.view_charity

  lateinit var presenter: CharityPresenter
  lateinit var eventHandler: CharityEventHandler

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    eventHandler = CharityEventHandler(activity!!)
    val onClick: (CharityViewModel.CharityDetails) -> Unit = { eventHandler.onClickCharity(it) }

    val adapter = RecyclerViewListAdapter(itemLayout, onClick)
    presenter = CharityPresenter(activity!!, emptyView, swipeToRefresh, list, adapter)
    binding.charityPresenter = presenter
  }
}
