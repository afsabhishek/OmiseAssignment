package com.example.omisetest.charity.presentation

import android.app.Activity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import com.example.omisetest.Configuration
import com.example.omisetest.Interactor
import com.example.omisetest.R
import com.example.omisetest.Response
import com.example.omisetest.gateway.CreateDonationRequest
import com.example.omisetest.presentation.charity.presentation.CharityViewModel
import com.example.omisetest.presentation.onDone
import java.math.BigDecimal

class DonationEventHandler(
  context: Activity,
  private var cardNumber: EditText,
  private var cvv: EditText,
  private var expiryDate: EditText,
  private var cardHolder: EditText,
  private var amount: AppCompatEditText,
  private var loader: ProgressBar,
  private var donateButton: AppCompatButton
) : Interactor(context) {

  private val selectedCharity = Configuration.deps.useCase.charity.getBrowseCharity
  private val donateMoney = Configuration.deps.useCase.charity.makeDonation

  var charityViewModel: CharityViewModel.CharityDetails? = null

  init {
    charityViewModel = selectedCharity.request()
    cardHolder.onDone {
      if (cardNumber.length() == 19 && (cvv.length() in 3..4) && expiryDate.length() == 5)
        amount.visibility = View.VISIBLE
      else
        errorDialogStyled(getString(R.string.error_card_details))
    }
    amount.onDone {
      donateButton.visibility = View.VISIBLE
    }
  }

  fun onDonate(view: View) = onClick(view) { donate() }

  private fun donate() {
    loader.visibility = View.VISIBLE
    bg {
      val response = donateMoney.request(CreateDonationRequest(
        name = charityViewModel?.name.toString(),
        token = charityViewModel?.id.toString(),
        amount = BigDecimal(amount.text.toString())
      ))
      ui {
        loader.visibility = View.GONE
        when (response) {
          is Response.Success -> alertDialogStyled("Success", DialogButtonAction(R.string.action_ok) { finishActivity() })
          is Response.Error -> showBffError(response)
          is Response.Failure -> showBffError(response)
        }
      }
    }
  }

  val cardNumberWatcher = object : TextWatcher {
    private val space = ' '
    private val numbers = "0123456789"

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
      var pos = 0
      while (true) {
        if (pos >= s.length) break
        if (space === s[pos] && ((pos + 1) % 5 != 0 || pos + 1 == s.length)) {
          s.delete(pos, pos + 1)
        } else {
          pos++
        }
      }

      pos = 4
      while (true) {
        if (pos >= s.length) break
        val c = s[pos]
        if (numbers.indexOf(c) >= 0) {
          s.insert(pos, "" + space)
        }
        pos += 5
      }
    }
  }

  val expiryWatcher = object : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
      if (s.length > 4) {
        return
      }
      if (s.length > 2 && s.toString()[2] != '/') {
        s.insert(2, "/")
      }
    }
  }
}