package com.rabotyagi.onboarding.hackaton.ui.frags.registration

import android.R
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.databinding.FragmentRegistrationBinding
import com.rabotyagi.onboarding.hackaton.ui._global.BaseFragment
import com.rabotyagi.onboarding.hackaton.ui.frags.selector.SingleSelectorDialogFragment
import com.rabotyagi.onboarding.hackaton.ui.frags.selector.SingleSelectorDialogFragment.Companion.ARG_DEPARTMENTS
import com.rabotyagi.onboarding.hackaton.ui.frags.selector.SingleSelectorDialogFragment.Companion.ARG_DEPARTMENT_ID
import com.rabotyagi.onboarding.hackaton.ui.frags.selector.SingleSelectorDialogFragment.Companion.ARG_ROLES
import com.rabotyagi.onboarding.hackaton.ui.frags.selector.SingleSelectorDialogFragment.Companion.ARG_ROLES_CODE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : BaseFragment(), SingleSelectorDialogFragment.Listener {
    private val registrationViewModel by viewModels<RegistrationViewModel>()

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private var department: Department? = null

    private var role: Role? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            if (!binding.firstName.text.toString().isNullOrEmpty() &&
                !binding.email.text.toString().isNullOrEmpty() &&
                !binding.lastName.text.toString().isNullOrEmpty() &&
                role != null &&
                department != null &&
                !binding.password.text.toString().isNullOrEmpty()
            )
                registrationViewModel.register(
                    firstName = binding.firstName.text.toString(),
                    email = binding.email.text.toString(),
                    lastname = binding.lastName.text.toString(),
                    role = role!!,
                    department = department!!,
                    password = binding.password.text.toString()
                )
            else Toast.makeText(
                requireContext(),
                "Вы не заполнили все поля",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.roles.setOnClickListener {
            SingleSelectorDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_ROLES_CODE, role?.code)
                        putBoolean(ARG_ROLES, true)
                    }
                }
                .show(childFragmentManager, null)
        }

        binding.departmentSpinner.setOnClickListener {
            SingleSelectorDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        department?.id?.let { it1 -> putInt(ARG_DEPARTMENT_ID, it1) }
                        putBoolean(ARG_DEPARTMENTS, true)
                    }
                }
                .show(childFragmentManager, null)
        }
    }

    override fun onResume() {
        super.onResume()
        registrationViewModel.apply {
            loading.subscribe { renderLoading(it) }.disposeOnPause()
            register.subscribe {
                Toast.makeText(
                    requireContext(),
                    "Вы зарегистрировали нового пользователя",
                    Toast.LENGTH_SHORT
                ).show()
                binding.continueButton.findNavController()
                    .navigate(com.rabotyagi.onboarding.hackaton.R.id.viewFragment)
            }.disposeOnPause()

            error.subscribe { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
                .disposeOnPause()
        }
    }

    private fun renderLoading(show: Boolean?) {
        binding.apply {
            if (show == true) {
                loaderContainer.visibility = View.VISIBLE
                progressBar.repeatCount = ValueAnimator.INFINITE
                progressBar.playAnimation()
            } else {
                loaderContainer.visibility = View.INVISIBLE
                progressBar.pauseAnimation()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDepartmentSelected(item: Department?) {
        binding.departmentSpinner.text = item?.name ?: ""
        department = item
    }

    override fun onRoleSelected(item: Role?) {
        binding.roles.text = item?.name ?: ""
        role = item
    }

    override fun onClose() {

    }
}