package com.rabotyagi.onboarding.hackaton.ui.registration

import androidx.lifecycle.ViewModel
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    init {
        fetchData()
    }

    private fun fetchData() {
        fetchDepartments()
        fetchRoles()
    }

    private fun fetchDepartments() {
        repository.apiService.getDepartments().subscribe {

        }
    }

    private fun fetchRoles() {
        repository.apiService.getRoles().subscribe {

        }
    }

    fun register(
        firstName: String,
        email: String,
        lastname: String,
        role: Role,
        department: Department,
        password: String
    ) {
        repository.register(
            UserData(
                "$firstName $lastname",
                email = email,
                roles = listOf(role.name),
                department = department,
                password = password

            )
        )

    }
}