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
            throw IllegalStateException("Счетчик не может быть меньше 0!")
        }
        return currentCount - 1
    }

    override suspend fun reset(): Int {
        delay(300)
        return 0
    }

    override fun observeCount(): Flow<Int> = flow {
        // Здесь могла бы быть реальная подписка на изменения в БД
        while (true) {
            delay(1000)
            // Эмуляция внешних изменений
        }
    }
}