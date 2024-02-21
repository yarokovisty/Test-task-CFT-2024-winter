package com.example.testtask.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

class UserRouterImpl(private val router: Router) : UsersRouter {
    override fun replaceItemUser(fragment: FragmentScreen) {
        router.replaceScreen(fragment)
    }

    override fun navigateTo(fragment: FragmentScreen) {
        router.navigateTo(fragment)
    }

    override fun backTo() {
        router.backTo(null)
    }
}