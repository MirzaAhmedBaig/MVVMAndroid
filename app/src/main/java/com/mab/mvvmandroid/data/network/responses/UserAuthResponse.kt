package com.mab.mvvmandroid.data.network.responses

import com.mab.mvvmandroid.data.db.entities.User

class UserAuthResponse {
    var token: String? = null
    var message: String? = null
    var user: User? = null
}