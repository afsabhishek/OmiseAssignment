package com.example.omisetest.api

import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.gateway.CreateDonationRequest
import com.example.omisetest.presentation.charity.presentation.CharityViewModel

object ConvertFromDomain {

  fun convert(it: CreateDonationRequest) = PostDonationRequest(
    name = it.name,
    token = it.token,
    amount = it.amount
  )

  fun convert(it: CharityDetails) = CharityViewModel.CharityDetails(
    id = it.id,
    logoUrl = it.logo_url,
    name = it.name
  )
}
