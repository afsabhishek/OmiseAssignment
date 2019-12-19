package com.example.omisetest.api

import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.domain.Donations

object ConvertToDomain {

  fun convert(it: PostDonationResponse) = Donations(
    success = it.success,
    error_code = it.error_code,
    error_message = it.error_message
  )

  fun convert(container: GetCharitiesResponse) = container.data.map {
    CharityDetails(
      id = it.id,
      name = it.name,
      logo_url = it.logo_url
    )
  }
}
