package com.example.testtask.ui

import com.example.testtask.screen.InfoUserFragment
import com.example.testtask.screen.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun usersScreen() = FragmentScreen { UsersFragment() }
    fun infoUserScreen() = FragmentScreen { InfoUserFragment() }
}