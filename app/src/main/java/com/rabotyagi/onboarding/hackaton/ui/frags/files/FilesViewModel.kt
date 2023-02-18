package com.rabotyagi.onboarding.hackaton.ui.frags.files

import com.jakewharton.rxrelay2.BehaviorRelay
import com.rabotyagi.onboarding.hackaton.data.model.File
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import com.rabotyagi.onboarding.hackaton.ui._global.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    val loading: Observable<Boolean> = loadingRelay.hide()

    private val dataRelay = BehaviorRelay.create<List<File>>()
    val data: Observable<List<File>> = dataRelay.hide()

    private val errorRelay = BehaviorRelay.create<String>()
    val error: Observable<String> = errorRelay.hide()

    init {
        fetchFiles()
    }

    private fun fetchFiles() {
        compositeDisposable.addAll(
            repository
                .fetchFiles()
                .doOnSubscribe {
                    loadingRelay.accept(true)
                }
                .doFinally {
                    loadingRelay.accept(false)
                }
                .subscribe({
                    dataRelay.accept(it)
                }, {
                    errorRelay.accept("Ошибка")
                })
        )
    }
}