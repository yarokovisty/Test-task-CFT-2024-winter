package com.example.testtask.navigation

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

class UserNavViewModel(private val router: UsersRouter) : ViewModel() {
    fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceItemUser(fragmentScreen)
    }

    fun navigateTo(fragmentScreen: FragmentScreen) {
        router.navigateTo(fragmentScreen)
    }
}