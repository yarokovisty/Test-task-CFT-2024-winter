package com.example.testtask.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testtask.data.user_model.Result

class ViewModelData : ViewModel() {
    val infoUser: MutableLiveData<Result> by lazy {
        MutableLiveData<Result>()
    }
}