package com.example.pokeme.presentation.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.fragment.app.activityViewModels
import com.example.pokeme.App
import com.example.pokeme.databinding.FragmentRegisterBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.RegisterForm
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseAuthFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val userViewModel: UserViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

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
            form.validate()
            if (!form.isValid) {
                displayErrors(form.errors)
                return@setOnClickListener
            }
            userViewModel.createUser(form.userEmail, binding.editPassword.text.toString())
        }

        userViewModel.isLoading.observe(viewLifecycleOwner, { onLoading(it) })
        userViewModel.exception.observe(viewLifecycleOwner, { onError(it) })
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
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}