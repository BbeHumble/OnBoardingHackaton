package com.rabotyagi.onboarding.hackaton.data.repository

import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.File
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import io.reactivex.Observable
import io.reactivex.Observer
import javax.inject.Inject

class Repository @Inject internal constructor(var apiService: ApiService) {
    fun fetchDepartmentList(): Observable<List<Department>> {
        return apiService.getDepartments()
    }

    fun register(userData: UserData) {
        return apiService.register(userData)
    }

    fun fetchRoles(): Observable<List<Role>> {
        return apiService.getRoles()
    }
    fun fetchFiles():Observable<List<File>>{
        return apiService.getFiles()
    }


}