package com.example.omisetest.charity.business

import com.example.omisetest.domain.NavigationCache

class GetSelectedCharityBrowseUseCase(
  private val getCache: () -> NavigationCache?
) {

  fun request() = getCache()?.browseCharity
}
