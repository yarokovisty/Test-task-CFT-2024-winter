package com.example.testtask

import androidx.fragment.app.Fragment

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity