package com.example.testtask.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.example.testtask.data.user_model.Result

class UserNavViewModel(private val router: UsersRouter) : ViewModel() {


    fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceItemUser(fragmentScreen)
    }

    fun navigateTo(fragmentScreen: FragmentScreen) {
        router.navigateTo(fragmentScreen)
    }

    fun backTo() {
        router.backTo()
    }


}