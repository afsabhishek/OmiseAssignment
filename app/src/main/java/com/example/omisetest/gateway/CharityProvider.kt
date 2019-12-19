package com.example.omisetest.gateway

import com.example.omisetest.Response
import com.example.omisetest.domain.CharityDetails

class CharityProvider(
  val request: () -> Response<List<CharityDetails>>
)
