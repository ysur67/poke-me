package com.example.pokeme.presentation.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.fragment.app.activityViewModels
import com.example.pokeme.App
import com.example.pokeme.databinding.FragmentLoginBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.LoginForm
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseAuthFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val userViewModel: UserViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

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

        userViewModel.isLoading.observe(viewLifecycleOwner, { onLoading(it) })

        userViewModel.exception.observe(viewLifecycleOwner, { if (it != null) onError(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onLoading(value: Boolean) {
        for (view in binding.root.allViews) {
            view.isEnabled = !value
        }
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