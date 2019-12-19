package com.example.omisetest.charity.presentation

import android.app.Activity
import com.example.omisetest.Configuration
import com.example.omisetest.Interactor
import com.example.omisetest.presentation.charity.presentation.CharityViewModel

class CharityEventHandler(
  context: Activity
) : Interactor(context) {

  private val browseTo = Configuration.deps.useCase.charity.browseCharity

  fun onClickCharity(viewModel: CharityViewModel.CharityDetails) {
    browseTo.request(viewModel)
    launch(DonationActivity::class)
  }
}