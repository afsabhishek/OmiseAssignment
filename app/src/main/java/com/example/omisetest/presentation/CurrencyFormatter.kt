package com.example.omisetest.presentation

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object CurrencyFormatter {

  private val locale = Locale("en", "AU")
  private val currencyFormat = DecimalFormat("$#,##0.00", DecimalFormatSymbols(locale))

  fun format(value: BigDecimal?, orElse: String = ""): String = when (value) {
    null -> orElse
    else -> currencyFormat.format(value)
  }
}
