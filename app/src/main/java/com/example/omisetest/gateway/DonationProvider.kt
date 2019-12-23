package com.example.omisetest.gateway

import com.example.omisetest.Response
import com.example.omisetest.domain.Donations

class DonationProvider(
  val request: (CreateDonationRequest) -> Response<Donations>
)
