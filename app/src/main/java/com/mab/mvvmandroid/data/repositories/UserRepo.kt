package com.mab.mvvmandroid.data.repositories

import com.mab.mvvmandroid.data.network.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepo {
    fun checkUserExist(email: String, onSuccess: (String?) -> Unit, onFailed: (String?) -> Unit) {
        ApiClient.instance.userExist(email).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailed(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    onSuccess(response.body()?.string())
                else
                    onFailed(response.errorBody()?.string())

            }
        })
    }
}