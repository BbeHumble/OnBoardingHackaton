package com.rabotyagi.onboarding.hackaton.ui.registration

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.model.UserData
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val loadingRelay = BehaviorRelay.create<Boolean>()
    val loading: Observable<Boolean> = loadingRelay.hide()

    private val registerRelay = BehaviorRelay.create<Boolean?>()
    val register: Observable<Boolean> = registerRelay.hide()

    private val rolesRelay = BehaviorRelay.create<List<Role>?>()
    val roles: Observable<List<Role>> = rolesRelay.hide()

    private val departmentsRelay = BehaviorRelay.create<List<Department>?>()
    val departments: Observable<List<Department>> = departmentsRelay.hide()

    private val errorRelay = BehaviorRelay.create<String>()
    val error: Observable<String> = errorRelay.hide()

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchDepartments()
        fetchRoles()
    }

    fun fetchDepartments() {
        repository.fetchDepartmentList().doOnSubscribe {
            loadingRelay.accept(true)
        }.doFinally {
            loadingRelay.accept(false)
        }.subscribe({
            departmentsRelay.accept(it)
        }, {
            errorRelay.accept("Ошибка запроса департаментов")
        })
    }

    fun fetchRoles() {
        repository.fetchRoles().doOnSubscribe {
            loadingRelay.accept(true)
        }.doFinally {
            loadingRelay.accept(false)
        }.subscribe({
            rolesRelay.accept(it)
        }, {
            errorRelay.accept("Ошибка запроса ролей")
        })
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
        ).doOnSubscribe {
            loadingRelay.accept(true)
        }.doFinally {
            loadingRelay.accept(false)
        }.subscribe({
            registerRelay.accept(true)
        }, {
            errorRelay.accept("Ошибка регистрации")
        })
    }
}