package com.example.dsv4.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsv4.data.repository.CounterRepository
import com.example.dsv4.data.repository.CounterRepositoryImpl
import com.example.dsv4.domain.models.CounterState
import com.example.dsv4.presentation.effect.CounterEffect
import com.example.dsv4.presentation.intent.CounterIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CounterViewModel(
    private val repository: CounterRepository = CounterRepositoryImpl()
) : ViewModel() {

    // Состояние UI
    private val _state = MutableStateFlow(CounterState())
    val state: StateFlow<CounterState> = _state.asStateFlow()

    // Канал для эффектов (одноразовые события)
    private val _effect = Channel<CounterEffect>()
    val effect: Flow<CounterEffect> = _effect.receiveAsFlow()

    // Обработка интентов (действий пользователя)
    fun handleIntent(intent: CounterIntent) {
        when (intent) {
            is CounterIntent.Increment -> increment()
            is CounterIntent.Decrement -> decrement()
            is CounterIntent.Reset -> reset()
            is CounterIntent.SetCount -> setCount(intent.value)
            is CounterIntent.ClearError -> clearError()
        }
    }

    private fun increment() {
        viewModelScope.launch {
            try {
                updateState { _state.value.copy(isLoading = true, error = null) }

                val newCount = repository.increment(_state.value.count)

                updateState {
                    _state.value.copy(
                        count = newCount,
                        isLoading = false,
                        lastOperation = "Increase to $newCount"
                    )
                }

                sendEffect(CounterEffect.ShowToast("+1 to the counter!"))

            } catch (e: Exception) {
                updateState {
                    _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error when zooming in"
                    )
                }
                sendEffect(CounterEffect.ShowToast("error: ${e.message}"))
            }
        }
    }

    private fun decrement() {
        viewModelScope.launch {
            try {
                updateState { _state.value.copy(isLoading = true, error = null) }

                val newCount = repository.decrement(_state.value.count)

                updateState {
                    _state.value.copy(
                        count = newCount,
                        isLoading = false,
                        lastOperation = "Reduction to $newCount"
                    )
                }

                if (newCount == 0) {
                    sendEffect(CounterEffect.PlaySound(1)) // ID звука достижения нуля
                }

            } catch (e: Exception) {
                updateState {
                    _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error when zooming out"
                    )
                }
                sendEffect(CounterEffect.ShowToast("Error: ${e.message}"))
            }
        }
    }

    private fun reset() {
        viewModelScope.launch {
            try {
                updateState { _state.value.copy(isLoading = true) }

                val newCount = repository.reset()

                updateState {
                    _state.value.copy(
                        count = newCount,
                        isLoading = false,
                        lastOperation = "Reset"
                    )
                }

                sendEffect(CounterEffect.ShowToast("The counter has been reset"))

            } catch (e: Exception) {
                updateState {
                    _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Reset error"
                    )
                }
            }
        }
    }

    private fun setCount(value: Int) {
        updateState {
            _state.value.copy(
                count = value,
                lastOperation = "Setting the value $value"
            )
        }
    }

    private fun clearError() {
        updateState { _state.value.copy(error = null) }
    }

    private fun updateState(update: (CounterState) -> CounterState) {
        _state.update { update(it) }
    }

    private fun sendEffect(effect: CounterEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}