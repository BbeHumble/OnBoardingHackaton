package com.rabotyagi.onboarding.hackaton.data.repository

import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import com.rabotyagi.onboarding.hackaton.schedule.SchedulersProvider
import io.reactivex.Completable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class Repository @Inject internal constructor(
    var apiService: ApiService,
    private val schedulers: SchedulersProvider
) {
    fun login(username: String, password: String): Completable {
        return apiService.login(
            username.toRequestBody("text/plain".toMediaTypeOrNull()),
            password.toRequestBody("text/plain".toMediaTypeOrNull())
        ).subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}