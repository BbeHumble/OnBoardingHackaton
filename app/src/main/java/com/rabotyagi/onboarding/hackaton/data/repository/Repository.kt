package com.rabotyagi.onboarding.hackaton.data.repository

import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject internal constructor(var apiService: ApiService) {
//    suspend fun login(phoneNumber: String, password: String): AuthorizationResponseBody {
//        return apiService.login(AuthorizationRequestBody(phoneNumber, password)).data
//    }

}