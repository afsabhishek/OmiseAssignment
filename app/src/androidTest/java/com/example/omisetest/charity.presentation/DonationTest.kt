package com.example.omisetest.presentation

import android.support.test.runner.AndroidJUnit4
import com.example.omisetest.Helpers.donation
import com.example.omisetest.Helpers.rule
import com.example.omisetest.charity.presentation.DonationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DonationTest {

  @get:Rule
  val activityTestRule = rule<DonationActivity>()

  @Test
  fun donationTest() {
    donation {
      enterCardNumber("5555 5555 5555 5555")
      enterCVV("243")
      enterExpiry("05/22")
      enterCardHolder("ABHISHEK KUMAR")
      enterAmount("100")
      tapDonate()
    } nextCharities {
      verifyCharityVisible(
        name = "Habitat for Humanity"
      )
    }
  }
}
