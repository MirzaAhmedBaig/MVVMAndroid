package com.mab.mvvmandroid.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailed(message: String)
}