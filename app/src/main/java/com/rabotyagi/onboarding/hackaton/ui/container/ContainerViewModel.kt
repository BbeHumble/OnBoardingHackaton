package com.rabotyagi.onboarding.hackaton.ui.container

import androidx.lifecycle.ViewModel
import com.rabotyagi.onboarding.hackaton.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class ContainerViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val disposables = CompositeDisposable()

//    private fun loadSome(loadGreetingUseCase: LoadGreetingUseCase) {
//        disposables.add(loadGreetingUseCase.execute()
//            .subscribeOn(schedulersFacade.io())
//            .observeOn(schedulersFacade.ui())
//            .doOnSubscribe { __ -> response.setValue(Response.loading()) }
//            .subscribe(
//                { greeting -> response.setValue(Response.success(greeting)) }
//            ) { throwable -> response.setValue(Response.error(throwable)) }
//        )
//    }
}