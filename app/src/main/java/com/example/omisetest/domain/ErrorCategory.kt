package com.example.omisetest.domain

enum class ErrorCategory(val httpStatus: Int? = null) {
  InvalidAccess(403),
  InvalidInput(400),
  ServerError(500),
  BadGateway(504),
  UnspecifiedClientError(),
  UnspecifiedServerError(),
  Unknown();

  fun statusString() = httpStatus.toString()
}
