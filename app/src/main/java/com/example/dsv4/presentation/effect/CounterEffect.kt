package com.example.dsv4.presentation.effect

sealed class CounterEffect {
    data class ShowToast(val message: String) : CounterEffect()
    object NavigateBack : CounterEffect()
    data class PlaySound(val soundId: Int) : CounterEffect()
}