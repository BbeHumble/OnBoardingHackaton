package com.rabotyagi.onboarding.hackaton.data.api

import io.reactivex.Completable
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    companion object {
        private const val API_CONST = "api"
    }

    @POST("$API_CONST/login")
    @Multipart
    fun login(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
    ): Completable
//
//    @GET("/api/v1/profile/")
//    suspend fun getProfile(): BaseResponse<Profile>
}