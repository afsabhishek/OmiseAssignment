package com.example.omisetest.api

import com.example.omisetest.Response
import com.example.omisetest.api.ConvertFromDomain.convert
import com.example.omisetest.api.ConvertToDomain.convert
import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.domain.Donations
import com.example.omisetest.domain.ErrorCategory
import com.example.omisetest.gateway.CreateDonationRequest
import com.google.gson.Gson
import okhttp3.ResponseBody

class MyBankConnector(private val client: MyBankClient) {

  private fun createDonation(r: PostDonationRequest): Response<PostDonationResponse> =
    client.createDonation(
      body = PostDonationBffBody(r.name, r.token, r.amount)
    ).asResponse()

  val createDonation: (CreateDonationRequest) -> Response<Donations> = {
    createDonation(convert(it)).map(::convert)
  }

  private fun fetchCharities(): Response<GetCharitiesResponse> =
    client.fetchCharities().asResponse()

  val fetchCharities: () -> Response<List<CharityDetails>> = {
    fetchCharities().map(::convert)
  }
}

fun <T> retrofit2.Call<T>.asResponse(
  convertStatus: (Int) -> ErrorCategory? = { null }
): Response<T> =
  try {
    with(execute()) {
      when (code()) {
        in 200..299 -> {
          val body = body()
          if (body == null) Response.Error("null body")
          else Response.Success(body)
        }
        else -> {
          Response.Failure(
            message = errorBody().`as`<GetCharitiesResponse.ErrorResponse>()?.error ?: message() ?: toString(),
            status = convertStatus(code()) ?: convertGeneralStatus(code())
          )
        }
      }
    }
  } catch (ex: Exception) {
    Response.Error(ex.message ?: ex.toString())
  }

fun convertGeneralStatus(status: Int): ErrorCategory = when (status) {
  500, 504 -> ErrorCategory.BadGateway
  else -> when (status) {
    in 400..499 -> ErrorCategory.UnspecifiedClientError
    in 500..599 -> ErrorCategory.UnspecifiedServerError
    else -> ErrorCategory.Unknown
  }
}

inline fun <reified T> ResponseBody?.`as`(): T? = try {
  Gson().fromJson(this?.string(), T::class.java)
} catch (ex: Exception) {
  null
}
