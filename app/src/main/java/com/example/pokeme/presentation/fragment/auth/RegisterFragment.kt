package com.example.pokeme.presentation.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokeme.databinding.FragmentRegisterBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.RegisterForm


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseAuthFragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener{
            val form = RegisterForm(
                binding.editEmail,
                binding.editPassword,
                binding.editPasswordRepeat
            )
            val isFormValid = userViewModel.isFormValid(form)
            if (!isFormValid) {
                displayErrors(form.errors)
                return@setOnClickListener
            }
            userViewModel.createUser(form.userEmail, binding.editPassword.text.toString())
        }

        userViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.register.isEnabled = !it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}