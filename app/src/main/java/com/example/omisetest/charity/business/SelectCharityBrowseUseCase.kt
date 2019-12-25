package com.example.omisetest.charity.business

import com.example.omisetest.domain.NavigationCache
import com.example.omisetest.presentation.charity.presentation.CharityViewModel

class SelectCharityBrowseUseCase(
    private val getCache: () -> NavigationCache?
) {

    fun request(viewModel: CharityViewModel.CharityDetails) {
        getCache()?.browseCharity = viewModel
    }
}
