package com.example.omisetest.charity.presentation

import android.app.Activity
import com.example.omisetest.Configuration
import com.example.omisetest.Interactor
import com.example.omisetest.presentation.charity.presentation.CharityViewModel

class DonationEventHandler(
  context: Activity
) : Interactor(context) {

  private val selectedCharity = Configuration.deps.useCase.charity.getBrowseCharity

  var charityViewModel: CharityViewModel.CharityDetails? = null

  init {
      charityViewModel = selectedCharity.request()
  }
}