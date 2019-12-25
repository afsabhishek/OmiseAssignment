package com.example.omisetest.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class AppBarActivity : AppCompatActivity() {

  abstract val activityLayout: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityLayout)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
    android.R.id.home -> {
      if (dismissAndBackEnabled) {
        onDismiss()
      }
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if (dismissAndBackEnabled) {
      onDismiss()
    }
  }

  var dismissAndBackEnabled: Boolean = true

  open fun onDismiss() {
    finish()
  }
}
