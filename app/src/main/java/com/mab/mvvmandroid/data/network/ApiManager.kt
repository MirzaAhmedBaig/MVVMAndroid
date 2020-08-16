package com.mab.mvvmandroid.data.network

import com.mab.mvvmandroid.data.network.responses.UserAuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiManager {
    @POST(EndPoints.LOGIN)
    suspend fun userLogin(@Body data: Any): Response<UserAuthResponse>
}