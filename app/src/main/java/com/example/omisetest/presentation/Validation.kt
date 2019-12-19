package com.example.omisetest.presentation

import android.support.design.widget.TextInputLayout

sealed class Validation<out A> {
  data class Success<out T>(val value: T) : Validation<T>()
  data class Failure<out T>(val error: String) : Validation<T>()

  fun then(
    onFailure: (String) -> Unit = {},
    onSuccess: (A) -> Unit
  ): Validation<A> = this.apply {
    when (this) {
      is Failure -> onFailure(error)
      is Success -> onSuccess(value)
    }
  }

  fun thenDefault(field: TextInputLayout) = then(
    onFailure = {
      field.error = it
    },
    onSuccess = {
      field.error = null
      if (it is String) {
        field.text = it
      }
    }
  )

  inline fun <reified B> map(
    func: (A) -> B
  ): Validation<B> = when (this) {
    is Validation.Failure -> Validation.Failure(error)
    is Validation.Success -> try {
      Validation.Success(func(value))
    } catch (ex: Exception) {
      Validation.Failure<B>("$value -> ${B::class.java}:\n\n$ex")
    }
  }

  fun <B> flatMap(func: (A) -> Validation<B>): Validation<B> = map(func).flatten()
}

fun <A> Validation<Validation<A>>.flatten(): Validation<A> = when (this) {
  is Validation.Failure -> Validation.Failure(error)
  is Validation.Success -> when (value) {
    is Validation.Failure -> Validation.Failure(value.error)
    is Validation.Success -> Validation.Success(value.value)
  }
}
