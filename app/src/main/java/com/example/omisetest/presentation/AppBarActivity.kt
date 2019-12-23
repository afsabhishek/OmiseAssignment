package com.example.omisetest.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.app_bar.toolbar

abstract class AppBarActivity : AppCompatActivity() {

  abstract val activityLayout: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityLayout)

//    setSupportActionBar(toolbar)
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
