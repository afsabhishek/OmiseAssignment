package com.example.omisetest.domain

import com.example.omisetest.presentation.charity.presentation.CharityViewModel

data class NavigationCache(
  var browseCharity: CharityViewModel.CharityDetails? = null
)
