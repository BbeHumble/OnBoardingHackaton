package com.rabotyagi.onboarding.hackaton.ui.frags.selector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.databinding.ItemListItemBinding
import com.rabotyagi.onboarding.hackaton.ui._global.BaseListAdapter
import com.rabotyagi.onboarding.hackaton.utils.visible

class DepartmentSingleSelectorAdapter(
    private val clickListener: (Department) -> Unit
) : BaseListAdapter<Department, ItemListItemBinding>(
    object : DiffUtil.ItemCallback<Department>() {
        override fun areItemsTheSame(
            oldItem: Department,
            newItem: Department
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Department,
            newItem: Department
        ): Boolean =
            oldItem == newItem
    }
) {
    var selectedPosition = 0

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemListItemBinding
        get() = ItemListItemBinding::inflate

    override fun bind(item: Department, view: View, position: Int) {
        super.bind(item, view, position)
        binding.container.setOnClickListener {
            clickListener.invoke(item)
        }
        binding.itemSingleSelection.text =
            item.name
        binding.checkbox.visible(position == selectedPosition)
    }
}