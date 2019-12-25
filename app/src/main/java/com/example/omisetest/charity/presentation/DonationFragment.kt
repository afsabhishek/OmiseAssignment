package com.example.omisetest.charity.presentation

import android.os.Bundle
import android.view.View
import com.example.omisetest.R
import com.example.omisetest.databinding.FragmentDonationBinding
import com.example.omisetest.presentation.DataBindingFragment
import kotlinx.android.synthetic.main.fragment_donation.*
import android.text.InputFilter

class DonationFragment : DataBindingFragment<FragmentDonationBinding>() {

  override val layoutId = R.layout.fragment_donation

  lateinit var eventHandler: DonationEventHandler

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    eventHandler = DonationEventHandler(activity!!, card_number, cvv_code, expired_date, card_holder)
    card_number.addTextChangedListener(eventHandler.cardNumberWatcher)
    card_holder.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
    expired_date.addTextChangedListener(eventHandler.expiryWatcher)
  }
}
