package com.rabotyagi.onboarding.hackaton.ui._global

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T, VB : ViewBinding>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseListAdapter<T, VB>.ViewHolder>(diffUtil) {

    private var _binding: VB? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = bindingInflater.invoke(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(_binding as ViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    open inner class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: T) {
            bind(item, itemView, absoluteAdapterPosition)
        }
    }

    open fun bind(item: T, view: View, position: Int) {}

}