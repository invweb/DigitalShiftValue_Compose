package com.example.dsv4.presentation.intent

sealed class CounterIntent {
    object Increment : CounterIntent()
    object Decrement : CounterIntent()
    object Reset : CounterIntent()
    data class SetCount(val value: Int) : CounterIntent()
    object ClearError : CounterIntent()
}