package com.example.omisetest

import com.example.omisetest.api.AndroidStorageClient
import com.example.omisetest.api.MyBankConnector
import com.example.omisetest.api.StorageConnector
import com.example.omisetest.api.buildMyBank
import com.example.omisetest.charity.business.DonationUseCase
import com.example.omisetest.charity.business.GetCharitiesUseCase
import com.example.omisetest.charity.business.GetSelectedCharityBrowseUseCase
import com.example.omisetest.charity.business.SelectCharityBrowseUseCase
import com.example.omisetest.domain.NavigationCache
import com.example.omisetest.gateway.CharityProvider
import com.example.omisetest.gateway.DonationProvider

object Configuration {

  private var cache: NavigationCache? = null
  private val getCache: () -> NavigationCache? = { cache }
  private val setCache: (NavigationCache?) -> Unit = { cache = it }

  val deps: Dependencies by lazy {
    val api = apis()
    val gateways = gateways(api)
    val useCases = useCases(gateways)

    Dependencies(
      useCase = useCases,
      gateway = gateways,
      api = api
    )
  }

  private fun apis(): APIs {
    val myBank = buildMyBank(ConfigurationEnv.host)
    val storage = StorageConnector(AndroidStorageClient { App.context })

    return APIs(
      myBank = myBank,
      storage = storage
    )
  }

  private fun gateways(api: APIs): Gateways {
    val charity = CharityProvider(api.myBank.fetchCharities)
    val donation = DonationProvider(api.myBank.createDonation)

    return Gateways(
      donation = donation,
      charity = charity
    )
  }

  private fun useCases(gateways: Gateways): UseCases {
    val charity = UseCaseCharity(
      charitiesList = GetCharitiesUseCase(gateways.charity),
      makeDonation = DonationUseCase(gateways.donation),
      browseCharity = SelectCharityBrowseUseCase(getCache),
      getBrowseCharity = GetSelectedCharityBrowseUseCase(getCache)
    )

    return UseCases(
      charity = charity
    )
  }

  data class Dependencies(
    val useCase: UseCases,
    val gateway: Gateways,
    val api: APIs
  )

  data class UseCases(
    val charity: UseCaseCharity
  )

  data class UseCaseCharity(
    val charitiesList: GetCharitiesUseCase,
    val makeDonation: DonationUseCase,
    val browseCharity: SelectCharityBrowseUseCase,
    val getBrowseCharity: GetSelectedCharityBrowseUseCase
  )

  data class Gateways(
    val donation: DonationProvider,
    val charity: CharityProvider
  )

  data class APIs(
    val myBank: MyBankConnector,
    val storage: StorageConnector
  )
}
