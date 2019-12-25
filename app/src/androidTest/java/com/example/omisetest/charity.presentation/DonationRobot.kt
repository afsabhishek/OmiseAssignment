package com.example.omisetest.charity.presentation

import com.example.omisetest.Helpers
import com.example.omisetest.R
import com.example.omisetest.presentation.Robot

class DonationRobot : Robot() {

  override fun title() = R.string.donation_title

  fun enterCardNumber(cardNumber: String) = setTextForId(R.id.card_number)(cardNumber)
  fun enterCVV(cvv: String) = setTextForId(R.id.cvv_code)(cvv)
  fun enterExpiry(expiry: String) = setTextForId(R.id.expired_date)(expiry)
  fun enterCardHolder(cardHolder: String) = setTextForId(R.id.card_holder)(cardHolder)
  fun enterAmount(amount: String) = setTextForId(R.id.amount)(amount)
  fun tapDonate() = tapId(R.id.donate_button)

  infix fun nextCharities(func: CharitiesRobot.() -> Unit) = Helpers.charities()
}
