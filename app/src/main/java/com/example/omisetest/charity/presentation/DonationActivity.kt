package com.example.omisetest.charity.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.omisetest.R
import kotlinx.android.synthetic.main.app_bar.*

class DonationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_donation)
    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.donation_title)
  }
}
