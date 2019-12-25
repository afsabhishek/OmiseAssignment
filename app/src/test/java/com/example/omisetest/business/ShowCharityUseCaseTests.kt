package com.example.omisetest.charity.business

import com.example.omisetest.Response
import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.gateway.CharityProvider
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.BehaviorSpec

class ShowCharityUseCaseTests : BehaviorSpec() {
  init {

    Given("a user") {
      val subject = useCaseLoggedIn()

      When("requesting a list of available charities") {
        val response = subject.request()

        Then("the request succeeds") {
          response shouldBe Response.Success(successBody())
        }
      }
    }
  }

  private fun providerReturn() = CharityProvider(
    request = { Response.Success(successBody()) }
  )

  private fun useCaseLoggedIn() = GetCharitiesUseCase(
    charityProvider = providerReturn()
  )

  private fun successBody() = listOf(
    CharityDetails(
      id = "7331",
      name = "Habitat for Humanity",
      logo_url = "http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif"
    )
  )
}
