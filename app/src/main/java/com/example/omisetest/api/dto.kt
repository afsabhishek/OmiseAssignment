package com.example.omisetest.api

import java.math.BigDecimal

data class PostDonationRequest(
  val token: String,
  val name: String,
  val amount: BigDecimal
)

data class PostDonationBffBody(
  val token: String,
  val name: String,
  val amount: BigDecimal
)

data class PostDonationResponse(
  val success: Boolean,
  val error_code: String,
  val error_message: String
)

data class GetCharitiesResponse(val data: List<Charity>) {
  data class Charity(
    val id: String,
    val name: String,
    val logo_url: String
  )

  data class ErrorResponse(val error: String)
}
