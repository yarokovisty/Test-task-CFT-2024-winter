package com.example.testtask.data.db

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()
    val getAmountUser: LiveData<Int> = userDao.getAmountUser()

    suspend fun addUser(users: List<User>) {
        userDao.addUser(users)
    }

    suspend fun updateUsers(users: List<User>) {
        userDao.updateUsers(users)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }


}