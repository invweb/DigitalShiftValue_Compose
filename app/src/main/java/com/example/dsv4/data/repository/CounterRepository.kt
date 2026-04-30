package com.example.dsv4.data.repository

import kotlinx.coroutines.flow.Flow

interface CounterRepository {
    suspend fun increment(currentCount: Int): Int
    suspend fun decrement(currentCount: Int): Int
    suspend fun reset(): Int
    fun observeCount(): Flow<Int>
}