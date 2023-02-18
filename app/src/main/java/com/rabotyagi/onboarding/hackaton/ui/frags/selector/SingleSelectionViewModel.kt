package com.rabotyagi.onboarding.hackaton.ui.frags.selector

import androidx.lifecycle.SavedStateHandle
import com.jakewharton.rxrelay2.BehaviorRelay
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import com.rabotyagi.onboarding.hackaton.ui._global.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class SingleSelectionViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    val loading: Observable<Boolean> = loadingRelay.hide()

    private val isRoles: Boolean? =
        savedStateHandle[SingleSelectorDialogFragment.ARG_ROLES]

    private val rolesCode: String? =
        savedStateHandle[SingleSelectorDialogFragment.ARG_ROLES_CODE]

    private val departmentId: Int? =
        savedStateHandle[SingleSelectorDialogFragment.ARG_DEPARTMENT_ID]
    private val isDepartment: Boolean? =
        savedStateHandle[SingleSelectorDialogFragment.ARG_DEPARTMENTS]

    private val rolesRelay = BehaviorRelay.create<List<Role>?>()
    val roles: Observable<Pair<Int, List<Role>>> = rolesRelay.hide().map { list ->
        val newList = ArrayList(list)
        var selectedPosition = -1
        selectedPosition = newList.indexOfFirst { it.code == rolesCode }
        selectedPosition to newList
    }

    private val departmentsRelay = BehaviorRelay.create<List<Department>?>()
    val departments: Observable<Pair<Int, List<Department>>> = departmentsRelay.hide().map { list ->
        val newList = ArrayList(list)
        var selectedPosition = -1
        selectedPosition = newList.indexOfFirst { it.id == departmentId }
        selectedPosition to newList
    }

    private val errorRelay = BehaviorRelay.create<String>()
    val error: Observable<String> = errorRelay.hide()

    init {
        fetchInfo()
    }

    private fun fetchInfo() {
        if (isRoles != null && isRoles) {
            fetchRoles()
        }
        if (isDepartment != null && isDepartment) {
            fetchDepartments()
        }

    }

    private fun fetchDepartments() {
       /* Single.just(
            listOf(
                Department(1,"qwe"),
                Department(2,"asd")
            )
        )*/
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

    private fun fetchRoles() {
       /* Single.just(
            listOf(
                Role("1","123"),
                Role("2","234")
            )
        )*/
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
}