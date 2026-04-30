package com.example.dsv4.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CounterRepositoryImpl : CounterRepository {

    override suspend fun increment(currentCount: Int): Int {
        // Имитация задержки сети/базы данных
        delay(300)
        return currentCount + 1
    }

    override suspend fun decrement(currentCount: Int): Int {
        delay(300)
        if (currentCount <= 0) {
            throw IllegalStateException("The counter must not be less than 0!")
        }
        return currentCount - 1
    }

    override suspend fun reset(): Int {
        delay(300)
        return 0
    }

    override fun observeCount(): Flow<Int> = flow {
        // There could be a real subscription for changes to the database here
        while (true) {
            delay(1000)
            // Emulation of external changes
        }
    }
}