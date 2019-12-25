package com.example.omisetest.presentation

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollTo
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withResourceName
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import android.support.test.espresso.UiController
import android.support.test.espresso.matcher.ViewMatchers.isClickable
import android.view.View
import org.hamcrest.Matcher

abstract class Robot {

  protected abstract fun title(): Int
  protected open fun permanentLabels(): List<Int> = emptyList()

  fun isCorrectScreen() {
    textIsDisplayed(title())
    permanentLabels().forEach { textIsDisplayed(it) }
  }

  protected fun iconIsDisplayed(resourceId: Int) {
    onView(withId(resourceId)).check(matches(isDisplayed()))
  }

  protected fun iconIsClickable(resourceId: Int) {
    onView(withId(resourceId)).check(matches(isClickable()))
  }

  protected fun textIsDisplayed(vararg texts: String) =
    texts.forEach { onView(withText(it)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))) }

  protected fun textIsDisplayed(vararg resourceIds: Int) =
    resourceIds.forEach { onView(withText(it)).check(matches(isDisplayed())) }

  protected fun elementIsDisplayed(vararg resourceIds: Int) =
    resourceIds.forEach { onView(withText(it)).check(matches(isDisplayed())) }

  protected fun tapId(resourceId: Int) {
    onView(withId(resourceId)).perform(click())
  }

  protected fun tapText(text: String) {
    onView(withText(text)).perform(click())
  }

  protected fun tapResourceID(resourceId: String) {
    onView(withResourceName(resourceId)).perform(click())
  }

  protected fun swipeIdRight(resourceId: Int) {
    onView(withId(resourceId)).perform(swipeRight())
  }

  protected fun swipeIdLeft(resourceId: Int) {
    onView(withId(resourceId)).perform(swipeLeft())
  }

  protected fun enterTextForId(resourceId: Int): (String) -> Unit = { text: String ->
    onView(withId(resourceId)).perform(typeText(text))
    Espresso.closeSoftKeyboard()
  }

  protected fun setTextForId(resourceId: Int): (String) -> Unit = { text: String ->
    onView(withId(resourceId)).perform(click())
    onView(withId(resourceId)).perform(replaceText(text))
    Espresso.closeSoftKeyboard()
  }

  private fun scrollInRecyclerViewToText(recyclerViewId: Int, text: String) {
    onView(withId(recyclerViewId)).perform(
      scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(text)))
    )
  }

  protected fun ensureItemScrollableIn(recyclerViewId: Int, identifyingText: String, vararg texts: String) {
    scrollInRecyclerViewToText(recyclerViewId, identifyingText)
    textIsDisplayed(identifyingText, *texts)
  }

  protected fun tapFirstItemRecylerView(recyclerViewId: Int) {
    onView(withId(recyclerViewId)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
  }

  protected fun tapSecondItemRecylerView(recyclerViewId: Int) {
    onView(withId(recyclerViewId)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
  }

  protected fun tapThirdItemRecylerView(recyclerViewId: Int) {
    onView(withId(recyclerViewId)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
  }

  protected fun tapFourthItemRecylerView(recyclerViewId: Int) {
    onView(withId(recyclerViewId)).perform(
      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
  }

  protected fun swipeUpScrollView(scrollViewId: Int) {
    onView(withId(scrollViewId)).perform(swipeUp())
  }

  protected fun findViewBy(elementId: Int): Boolean {
    val check = onView(withId(elementId)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    return check != null
  }

  protected fun tapHome() {
    onView(withContentDescription("Navigate up")).perform(click())
  }

  fun handleConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> {
        return constraints
      }

      override fun getDescription(): String {
        return action.description
      }

      override fun perform(uiController: UiController, view: View) {
        action.perform(uiController, view)
      }
    }
  }

  fun clickbutton(resourceId: Int) {
    onView(withId(resourceId)).perform(handleConstraints(click(), isDisplayingAtLeast(55)))
  }

  fun clickbuttonWithText(resourceText: String) {
    onView(withText(resourceText)).perform(handleConstraints(click(), isDisplayingAtLeast(55)))
  }
}
