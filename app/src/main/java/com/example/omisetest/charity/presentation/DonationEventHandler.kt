package com.example.omisetest.charity.presentation

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.omisetest.Configuration
import com.example.omisetest.Interactor
import com.example.omisetest.presentation.charity.presentation.CharityViewModel

class DonationEventHandler(
  context: Activity,
  cardNumber: EditText,
  cvv: EditText,
  expiryDate: EditText,
  cardHolder: EditText
) : Interactor(context) {

  private val selectedCharity = Configuration.deps.useCase.charity.getBrowseCharity

  var charityViewModel: CharityViewModel.CharityDetails? = null

  init {
    charityViewModel = selectedCharity.request()
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