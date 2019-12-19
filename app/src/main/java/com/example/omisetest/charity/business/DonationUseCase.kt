package com.example.omisetest.charity.business

import com.example.omisetest.Response
import com.example.omisetest.domain.Donations
import com.example.omisetest.gateway.CreateDonationRequest
import com.example.omisetest.gateway.DonationProvider

class DonationUseCase(
  private val donationProvider: DonationProvider
) {

  fun request(request: CreateDonationRequest): Response<Donations> =  donationProvider.request(request)
}
