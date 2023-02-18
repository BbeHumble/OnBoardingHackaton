package com.rabotyagi.onboarding.hackaton.ui.frags.files

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.data.model.File
import com.rabotyagi.onboarding.hackaton.databinding.FragmentFilesBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class FilesFragment : Fragment() {
    private val viewModel by viewModels<FilesViewModel>()

    private val disposeOnPauseDisposables = CompositeDisposable()

    private var _binding: FragmentFilesBinding? = null
    private val binding get() = _binding!!

    private fun renderFiles(files: List<File>) {
        binding.filesList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = FilesAdapter(files) { file ->
            openFile(file)
        }
        binding.filesList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            disposeOnPauseDisposables.addAll(
                loading.subscribe {
//                    renderLoading(it)
                },
                data.subscribe { files ->
                    renderFiles(files)
                },
                error.subscribe {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    override fun onPause() {
        super.onPause()
        disposeOnPauseDisposables.clear()
    }

    private fun openFile(file: File?) {
        findNavController().navigate(
            R.id.filesFragment,
            bundleOf(getString(R.string.URL_KEY) to file?.fileUrl)
        )
    }
}