package com.rabotyagi.onboarding.hackaton.ui.files

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.data.model.File

class FilesAdapter(
    val data: List<File>,
    private val onSelect: (File?) -> Unit
) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileTitle: TextView = view.findViewById(R.id.fileTitle) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.li_file, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileData = data[position]
        holder.itemView.setOnClickListener {
            onSelect(fileData)
        }
        holder.fileTitle.text = fileData.title
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

