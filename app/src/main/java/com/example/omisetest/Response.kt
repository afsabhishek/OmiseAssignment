package com.example.omisetest

import com.example.omisetest.domain.ErrorCategory

sealed class Response<out A> {
  data class Error<out T>(val message: String) : Response<T>()
  data class Failure<out T>(val message: String, val status: ErrorCategory) : Response<T>()
  data class Success<out T>(val body: T) : Response<T>()

  inline fun <reified B> map(func: (A) -> B): Response<B> = when (this) {
    is Response.Error -> Response.Error(message)
    is Response.Failure -> Response.Failure(message, status)
    is Response.Success -> try {
      Response.Success(func(body))
    } catch (ex: Exception) {
      Response.Error<B>("$body -> ${B::class.java}:\n\n$ex")
    }
  }

  fun <B> flatMap(func: (A) -> Response<B>): Response<B> = map(func).flatten()

  override fun equals(other: Any?): Boolean = when (this) {
    is Response.Error ->
      if (other is Response.Error<*>)
        message == other.message
      else false
    is Response.Failure ->
      if (other is Response.Failure<*>)
        message == other.message && status == other.status
      else false
    is Response.Success ->
      if (other is Response.Success<*>)
        body == other.body
      else false
  }

  override fun toString(): String = when (this) {
    is Response.Error -> """Response.Error<message="$message">"""
    is Response.Failure -> """Response.Failure<message="$message",httpStatus="$status">"""
    is Response.Success -> """Response.Success<body="$body">"""
  }
}

fun <A> Response<Response<A>>.flatten(): Response<A> = when (this) {
  is Response.Error -> Response.Error(message)
  is Response.Failure -> Response.Failure(message, status)
  is Response.Success -> when (body) {
    is Response.Error -> Response.Error(body.message)
    is Response.Failure -> Response.Failure(body.message, body.status)
    is Response.Success -> Response.Success(body.body)
  }
}
