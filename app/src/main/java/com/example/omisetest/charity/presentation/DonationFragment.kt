package com.example.omisetest.charity.presentation

import android.os.Bundle
import android.view.View
import com.example.omisetest.R
import com.example.omisetest.databinding.FragmentDonationBinding
import com.example.omisetest.presentation.DataBindingFragment

class DonationFragment : DataBindingFragment<FragmentDonationBinding>() {

  override val layoutId = R.layout.fragment_donation

  lateinit var eventHandler: DonationEventHandler

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    eventHandler = DonationEventHandler(activity!!)
  }
}
