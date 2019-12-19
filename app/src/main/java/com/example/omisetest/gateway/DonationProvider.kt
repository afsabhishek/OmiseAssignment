package com.example.omisetest.gateway

import au.com.mylifemyfinance.mobile.domain.Donations
import com.example.omisetest.Response

class DonationProvider(
  val request: (CreateDonationRequest) -> Response<Donations>
)
