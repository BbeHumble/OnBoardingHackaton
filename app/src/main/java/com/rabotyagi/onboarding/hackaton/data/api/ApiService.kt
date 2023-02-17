package com.rabotyagi.onboarding.hackaton.data.api

import io.reactivex.Completable
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POS

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
    @GET("$API_CONST/v1/profile/")
    fun getDepartments(): Observable<List<Department>>

    @GET("$API_CONST/user/roles")
    fun getRoles(): Observable<List<Role>>

    @POST("$API_CONST/user")
    fun register(userData: UserData): Unit
}