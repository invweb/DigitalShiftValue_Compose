package com.example.dsv4.domain.models

data class CounterState(
    val count: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastOperation: String? = null
)
