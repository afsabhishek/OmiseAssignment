package com.example.omisetest

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import com.example.omisetest.charity.presentation.CharitiesRobot
import com.example.omisetest.charity.presentation.DonationRobot
import com.example.omisetest.presentation.Robot

object Helpers {

  inline fun <reified A : Activity> rule(
    initialTouchMode: Boolean = true,
    launchActivity: Boolean = true
  ): ActivityTestRule<A> =
    ActivityTestRule(A::class.java, initialTouchMode, launchActivity).apply {
      charities()
    }

  private fun <T : Robot> withRobot(robot: T, func: T.() -> Unit) = robot.apply {
    isCorrectScreen()
    func()
  }

  //Charity
  fun charities() = Configuration.deps.useCase.charity.charitiesList.request()

  fun charity(func: CharitiesRobot.() -> Unit) = withRobot(CharitiesRobot(), func)
  //Donation
  fun donation(func: DonationRobot.() -> Unit) = withRobot(DonationRobot(), func)
}
