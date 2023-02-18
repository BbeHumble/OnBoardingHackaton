package com.rabotyagi.onboarding.hackaton.utils

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rabotyagi.onboarding.hackaton.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> argument(
    key: String,
    defaultValue: T? = null
): ReadWriteProperty<Fragment, T> =
    BundleExtractorDelegate { thisRef ->
        extractFromBundle(
            bundle = thisRef.arguments,
            key = key,
            defaultValue = defaultValue
        )
    }

class BundleExtractorDelegate<R, T>(private val initializer: (R) -> T) : ReadWriteProperty<R, T> {

    private object EMPTY

    private var value: Any? = EMPTY

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == EMPTY) {
            value = initializer(thisRef)
        }
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}

inline fun <reified T> extractFromBundle(
    bundle: Bundle?,
    key: String,
    defaultValue: T? = null
): T {
    val result = bundle?.get(key) ?: defaultValue
    if (result != null && result !is T) {
        throw ClassCastException("Property $key has different class type")
    }
    return result as T
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}


fun Dialog.setupFullHeight() {
    (this as BottomSheetDialog).setOnShowListener {
        val bottomSheetDialog = it as BottomSheetDialog
        val bottomSheet =
            this.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)

        val layoutParams = bottomSheet.layoutParams
        val windowHeight: Int = bottomSheetDialog.context.resources.displayMetrics.heightPixels
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams

        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }
    }
}
