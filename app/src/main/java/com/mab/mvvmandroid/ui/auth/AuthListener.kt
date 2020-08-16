package com.mab.mvvmandroid.ui.auth

import com.mab.mvvmandroid.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailed(message: String)
}