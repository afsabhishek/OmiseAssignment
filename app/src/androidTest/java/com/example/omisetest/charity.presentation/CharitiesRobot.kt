package com.example.omisetest.charity.presentation

import com.example.omisetest.Helpers.donation
import com.example.omisetest.R
import com.example.omisetest.presentation.Robot

class CharitiesRobot : Robot() {

  override fun title() = R.string.charity_title

  fun verifyCharityVisible(
    name: String
  ) = ensureItemScrollableIn(R.id.list, name)

  fun tapFirstItem() = tapFirstItemRecylerView(R.id.list)
  infix fun nextDonation(func: DonationRobot.() -> Unit) = donation(func)
}
