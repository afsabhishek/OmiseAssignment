package com.example.omisetest.presentation.charity.presentation

import com.example.omisetest.presentation.CharityViewModel

data class CharityViewModel(val data: List<CharityDetails>) {
  data class CharityDetails(
    override val id: String,
    override val name: String,
    override val logoUrl: String
  ) : CharityViewModel(id, name, logoUrl)
}
