package com.rabotyagi.onboarding.hackaton.ui.frags.login

import com.jakewharton.rxrelay2.BehaviorRelay
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import com.rabotyagi.onboarding.hackaton.ui._global.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {

    private val loadingRelay = BehaviorRelay.create<Boolean>()
    val loading: Observable<Boolean> = loadingRelay.hide()

    private val dataRelay = BehaviorRelay.create<Boolean?>()
    val data: Observable<Boolean> = dataRelay.hide()

    private val errorRelay = BehaviorRelay.create<String>()
    val error: Observable<String> = errorRelay.hide()

    /*private val errorMessageRelay = BehaviorRelay.create<SingleEvent<String>>()
    val errorMessage: Observable<SingleEvent<String>> = errorMessageRelay.hide()*/

    fun login(username: String, password: String) {
        compositeDisposable.addAll(
            repository
                .login(username, password)
                .doOnSubscribe {
                    loadingRelay.accept(true)
                }
                .doFinally {
                    loadingRelay.accept(false)
                }
                .subscribe({
                           dataRelay.accept(true)
                }, {
                    errorRelay.accept("Ошибка")
                })
        )
    }
}