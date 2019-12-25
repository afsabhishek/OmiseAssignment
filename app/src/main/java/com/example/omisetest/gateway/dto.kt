package com.example.omisetest.gateway

import java.math.BigDecimal

data class CreateDonationRequest(val name: String, val token: String, val amount: BigDecimal)
