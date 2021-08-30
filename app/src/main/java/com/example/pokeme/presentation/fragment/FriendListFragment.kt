package com.example.pokeme.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeme.R
import com.example.pokeme.data.models.Account
import com.example.pokeme.databinding.FragmentFriendListBinding
import com.example.pokeme.domain.AccountViewModel
import com.example.pokeme.presentation.adapter.FriendsRecyclerAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [FriendListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendListFragment : Fragment() {
    private var _binding: FragmentFriendListBinding? = null
    private val binding get() = _binding!!

    private val accountViewModel: AccountViewModel by activityViewModels()
    private lateinit var friendsRecyclerAdapter: FriendsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        friendsRecyclerAdapter = FriendsRecyclerAdapter(ArrayList())
        binding.recyclerView.adapter = friendsRecyclerAdapter

        accountViewModel.updateFriends()
        accountViewModel.friends.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            friendsRecyclerAdapter.add(it)
        })
        accountViewModel.isLoading.observe(viewLifecycleOwner, {
            print(it)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment FriendListFragment.
         */
        @JvmStatic
        fun newInstance() = FriendListFragment()
    }
}