package com.example.testtask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val picture: String?,
    val birthday: String?,
    val sex: String?,
    val age: String?,
    val country: String?,
    val state: String?,
    val city: String?,
    val address: String?,
    val timezone: String?,
    val postcode: String?,
    val email: String?,
    val phone: String?,
    val cell: String?,
    val location: String?
)