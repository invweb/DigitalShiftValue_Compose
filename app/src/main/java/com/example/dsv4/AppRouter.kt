package com.example.dsv4

interface AppRouter {
    fun navigateToHome()
    fun navigateToProfile(userId: String)
    fun navigateToSettings()
    fun goBack()
}