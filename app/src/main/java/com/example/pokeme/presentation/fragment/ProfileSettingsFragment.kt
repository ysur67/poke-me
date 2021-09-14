package com.example.pokeme.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokeme.App
import com.example.pokeme.data.models.Account
import com.example.pokeme.databinding.FragmentProfileSettingsBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.AccountViewModel
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSettingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val accountViewModel: AccountViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentProfileSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUsername(accountViewModel.account.value?.username)
        binding.reset.setOnClickListener{
            setUsername(accountViewModel.account.value?.username)
        }
        binding.save.setOnClickListener{
            val newAccount = Account(
                accountViewModel.account.value!!.email,
                binding.editUsername.text.toString()
            )
            accountViewModel.updateAccount(newAccount)
        }
    }

    private fun setUsername(value: String?) {
        binding.editUsername.setText(value ?: "")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment ProfileSettingsFragment.
         */
        @JvmStatic
        fun newInstance() = ProfileSettingsFragment()
    }
}