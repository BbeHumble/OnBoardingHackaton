package com.rabotyagi.onboarding.hackaton.data.repository

import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import com.rabotyagi.onboarding.hackaton.schedule.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.File
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import io.reactivex.Single
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

    fun fetchDepartmentList(): Single<List<Department>> {
        return apiService.getDepartments().subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun register(userData: UserData): Completable {
        return apiService.register(userData).subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
    fun fetchFiles(): io.reactivex.Observable<List<File>> {
        return apiService.getFiles()
    }

    fun fetchRoles(): Single<List<Role>> {
        return apiService.getRoles().subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}