package com.mab.mvvmandroid.data.repositories

import com.mab.mvvmandroid.data.db.AppDatabase
import com.mab.mvvmandroid.data.db.entities.User
import com.mab.mvvmandroid.data.network.ApiManager
import com.mab.mvvmandroid.data.network.SafeApiRequest
import com.mab.mvvmandroid.data.network.requests.UserAuthRequest
import com.mab.mvvmandroid.data.network.responses.UserAuthResponse

class UserRepository(private val apiManager: ApiManager, private val db: AppDatabase) :
    SafeApiRequest() {
    suspend fun performUserAuth(email: String, password: String): UserAuthResponse {
        return apiRequest { apiManager.userLogin(UserAuthRequest(email, password)) }
    }


    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}