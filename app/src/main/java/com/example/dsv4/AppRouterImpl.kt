package com.example.dsv4

import androidx.navigation.NavController

class AppRouterImpl(private val navController: NavController) : AppRouter {

    override fun navigateToHome() {
//        navController.navigate(R.id.homeFragment)
    }

    override fun navigateToProfile(userId: String) {
//        val bundle = bundleOf("userId" to userId)
//        navController.navigate(R.id.profileFragment, bundle)
    }

    override fun navigateToSettings() {
//        navController.navigate(R.id.settingsFragment)
    }

    override fun goBack() {
        navController.popBackStack()
    }
}