package com.rabotyagi.onboarding.hackaton.ui.registration

import android.R
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.rabotyagi.onboarding.hackaton.data.model.Department
import com.rabotyagi.onboarding.hackaton.data.model.Role
import com.rabotyagi.onboarding.hackaton.databinding.FragmentRegistrationBinding
import com.rabotyagi.onboarding.hackaton.ui._global.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : BaseFragment() {
    private val registrationViewModel by viewModels<RegistrationViewModel>()

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!


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
                binding.email.text.toString().isNullOrEmpty() &&
                !binding.lastName.text.toString().isNullOrEmpty() &&
                binding.roles.selectedItem.toString().isNullOrEmpty() &&
                !binding.departmentSpinner.selectedItem.toString().isNullOrEmpty() &&
                !binding.password.text.toString().isNullOrEmpty()
            )
                registrationViewModel.register(
                    firstName = binding.firstName.text.toString(),
                    email = binding.email.text.toString(),
                    lastname = binding.lastName.text.toString(),
                    role = binding.roles.selectedItem as Role,
                    department = binding.departmentSpinner.selectedItem as Department,
                    password = binding.password.text.toString()
                )
            else  Toast.makeText(
                requireContext(),
                "Вы не заполнили все поля",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.departmentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.roles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
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
            }.disposeOnPause()
            departments.subscribe {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    R.layout.simple_spinner_item, it.map { department -> department.name }
                )
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.departmentSpinner.adapter = adapter
            }.disposeOnPause()
            roles.subscribe {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    R.layout.simple_spinner_item, it.map { role -> role.name }
                )
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.roles.adapter = adapter
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
}