package com.rabotyagi.onboarding.hackaton.ui._global

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {
    private val disposeOnPauseDisposables = CompositeDisposable()

    override fun onPause() {
        super.onPause()
        disposeOnPauseDisposables.clear()
    }

    protected fun Disposable.disposeOnPause() {
        disposeOnPauseDisposables.add(this)
    }

}