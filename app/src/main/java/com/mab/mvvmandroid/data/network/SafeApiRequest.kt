package com.mab.mvvmandroid.data.network

import com.mab.mvvmandroid.utils.ApiExceptions
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = try {
            call()
        } catch (e: Exception) {
            e.printStackTrace()
            throw ApiExceptions(e.message, null)
        }
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val errorMessage = response.errorBody()?.string()?.let {
                try {
                    JSONObject(it).getString("message")
                } catch (e: JSONException) {
                    null
                }
            }
            throw ApiExceptions(errorMessage, response.code())
        }
    }
}