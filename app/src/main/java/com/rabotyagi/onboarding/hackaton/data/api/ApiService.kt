package com.rabotyagi.onboarding.hackaton.data.api

import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.File
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //    @POST("/api/v1/auth/login")
//    suspend fun login(@Body authorizationBody: AuthorizationRequestBody): BaseResponse<AuthorizationResponseBody>
//
    @GET("/api/v1/profile/")
    fun getDepartments(): Observable<List<Department>>

    @GET("/api/user/roles")
    fun getRoles(): Observable<List<Role>>

    @POST("/api/user")
    fun register(userData: UserData): Unit

    @POST("/api/user")
    fun getFiles(): Observable<List<File>>
}