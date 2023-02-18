package com.rabotyagi.onboarding.hackaton.ui.frags.selector

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.databinding.FragmentSelectorBinding
import com.rabotyagi.onboarding.hackaton.utils.argument
import com.rabotyagi.onboarding.hackaton.utils.setupFullHeight
import com.rabotyagi.onboarding.hackaton.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class SingleSelectorDialogFragment : BottomSheetDialogFragment() {
    companion object {
        const val ARG_APP_ID = "ARG_APP_ID"
        const val ARG_SELECTED = "ARG_SELECTED"
        const val ARG_ROLES = "ARG_ROLES"
        const val ARG_ROLES_CODE = "ARG_ROLES_CODE"
        const val ARG_DEPARTMENT_ID = "ARG_DEPARTMENT_ID"
        const val ARG_DEPARTMENTS = "ARG_DEPARTMENTS"

        fun createInstance(id: Int?, typeKey: String, typeValue: Boolean) =
            SingleSelectorDialogFragment().apply {
                arguments = bundleOf(
                    ARG_SELECTED to id,
                    typeKey to typeValue
                )
            }
    }

    val disposables = CompositeDisposable()

    private val viewModel by viewModels<SingleSelectionViewModel>()

    private var _binding: FragmentSelectorBinding? = null
    private val binding get() = _binding!!

    private val departmentSingleSelectorAdapter by lazy {
        DepartmentSingleSelectorAdapter {
            (parentFragment as? Listener)?.onDepartmentSelected(it)
            dismiss()
        }
    }

    private val roleSingleSelectorAdapter by lazy {
        RoleSingleSelectorAdapter {
            (parentFragment as? Listener)?.onRoleSelected(it)
            dismiss()
        }
    }
    private val isRoles: Boolean? by argument(ARG_ROLES)
    private val isDepartment: Boolean? by argument(ARG_DEPARTMENTS)

    interface Listener {
        fun onDepartmentSelected(item: Department?)
        fun onRoleSelected(item: Role?)
        fun onClose()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setupFullHeight()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            closeButton.setOnClickListener {
                dismiss()
            }

            if (isRoles == true) {
                tvSingleSelectionTitle.text = "Роли"
                rwSingleSelection.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = roleSingleSelectorAdapter
                }
            }
            if (isDepartment == true) {
                tvSingleSelectionTitle.text = "Департаменты"
                rwSingleSelection.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = departmentSingleSelectorAdapter
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            disposables.addAll(
                roles.subscribe { (selectedPosition, data) ->
                    roleSingleSelectorAdapter.selectedPosition = selectedPosition
                    roleSingleSelectorAdapter.submitList(data)
                },
                departments.subscribe { (selectedPosition, data) ->
                    departmentSingleSelectorAdapter.selectedPosition = selectedPosition
                    departmentSingleSelectorAdapter.submitList(data)
                },
                loading.subscribe { binding.mfcSelectionProgressBar.visible(it) },
                error.subscribe { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        disposables.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}