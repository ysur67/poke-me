package com.example.pokeme.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokeme.R


/**
 * A simple [Fragment] subclass.
 * Use the [PokesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokes, container, false)
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