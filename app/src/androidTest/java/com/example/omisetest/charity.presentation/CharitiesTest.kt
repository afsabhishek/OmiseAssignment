package com.example.omisetest.presentation

import android.support.test.runner.AndroidJUnit4
import com.example.omisetest.Helpers.charity
import com.example.omisetest.Helpers.rule
import com.example.omisetest.charity.presentation.CharityActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharitiesTest {

  @get:Rule
  val activityTestRule = rule<CharityActivity>()

  @Test
  fun charitiesTest() {
    charity {
      verifyCharityVisible(
        name = "Habitat for Humanity"
      )
      tapFirstItem()
    }
  }
}
