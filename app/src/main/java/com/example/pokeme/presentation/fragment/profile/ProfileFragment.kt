package com.example.pokeme.presentation.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokeme.App
import com.example.pokeme.R
import com.example.pokeme.databinding.FragmentProfileBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.AccountViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val accountViewModel: AccountViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.accountUsername.text = accountViewModel.account.value?.username ?: "No data"
        binding.changeAccountInfoButton.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_profileSettingsFragment)
        }
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
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}