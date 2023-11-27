package com.cafealyzer.cafealyzer.remote.service

import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("token")
    fun login(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<LoginResponse>
}