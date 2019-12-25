package com.example.omisetest

import android.support.test.runner.AndroidJUnit4
import com.example.omisetest.Helpers.charities
import com.example.omisetest.Helpers.charity
import com.example.omisetest.Helpers.rule
import com.example.omisetest.charity.presentation.CharityActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SmokeFlowTest {

    @get:Rule
    val activityTestRule = rule<CharityActivity>()

    @Test
    fun smokeTest() {
        charities()
        charity {
            verifyCharityVisible(
              name = "Habitat for Humanity"
            )
            tapFirstItem()
        } nextDonation {
            enterCardNumber("5555 5555 5555 5555")
            enterCVV("243")
            enterExpiry("05/22")
            enterCardHolder("ABHISHEK KUMAR")
            enterAmount("100")
            tapDonate()
        }
    }
}
