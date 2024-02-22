package com.example.testtask.retrofit

import com.example.testtask.data.Constants.AMOUNT_PERSON
import com.example.testtask.data.user_model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MainApi {
    @GET("/")
    suspend fun getUsers(@Query("results") results: Int = AMOUNT_PERSON): UsersResponse
}