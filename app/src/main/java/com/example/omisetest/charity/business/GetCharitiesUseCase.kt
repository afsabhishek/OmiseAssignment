package com.example.omisetest.charity.business

import com.example.omisetest.Response
import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.gateway.CharityProvider

class GetCharitiesUseCase(
  private val charityProvider: CharityProvider
) {

  fun request(): Response<List<CharityDetails>> = charityProvider.request()
}
