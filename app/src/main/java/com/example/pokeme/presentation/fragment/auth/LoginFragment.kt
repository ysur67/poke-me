package com.example.pokeme.presentation.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pokeme.databinding.FragmentLoginBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.utils.fragment.makeSnack
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseAuthFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener{
            val form = LoginForm(binding.editEmail, binding.editPassword)
            val isFormValid = userViewModel.isFormValid(form)
            if (!isFormValid) {
                displayErrors(form.errors)
                return@setOnClickListener
            }
            userViewModel.loginUser(form.userEmail, binding.editPassword.text.toString())
        }

        userViewModel.exception.observe(viewLifecycleOwner, {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> {
                    makeSnack("Проверьте введенные данные")
                }
            }
        })

        userViewModel.isLoading.observe(viewLifecycleOwner, {
            onLoading(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onLoading(loading: Boolean) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment LoginFragment.
         */
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}