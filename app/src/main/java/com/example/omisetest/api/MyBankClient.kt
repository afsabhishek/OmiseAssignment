package com.example.omisetest.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyBankClient {

  @POST("donations")
  fun createDonation(
    @Body body: PostDonationBffBody
  ): Call<PostDonationResponse>

  @GET("charities")
  fun fetchCharities(
  ): Call<GetCharitiesResponse>
}
