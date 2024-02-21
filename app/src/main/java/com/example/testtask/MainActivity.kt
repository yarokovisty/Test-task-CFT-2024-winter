package com.example.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testtask.navigation.App
import com.example.testtask.navigation.UserNavViewModel
import com.example.testtask.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {
    private val navigatorHolder = App.INSTANCE.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragmentScreen)
    private val viewModel = UserNavViewModel(App.INSTANCE.userRouter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            viewModel.replaceScreen(Screens.usersScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}