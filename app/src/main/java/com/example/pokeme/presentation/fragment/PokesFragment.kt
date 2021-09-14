package com.example.pokeme.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeme.App
import com.example.pokeme.R
import com.example.pokeme.databinding.FragmentPokesBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.MessageViewModel
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [PokesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val messageViewModel: MessageViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentPokesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            messageViewModel.sendMessage(
                binding.editTextTextPersonName.text.toString(),
                "jopa",
                "jopa"
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment PokesFragment.
         */
        @JvmStatic
        fun newInstance() = PokesFragment()
    }
}