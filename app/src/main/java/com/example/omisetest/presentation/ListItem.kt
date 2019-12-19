package com.example.omisetest.presentation

interface ListItem<out T> {
  val layoutId: Int
  val viewModel: T
}
