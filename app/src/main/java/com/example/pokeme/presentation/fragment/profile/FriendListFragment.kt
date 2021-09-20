package com.example.pokeme.presentation.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokeme.App
import com.example.pokeme.databinding.FragmentFriendListBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.AccountViewModel
import com.example.pokeme.presentation.adapter.FriendsRecyclerAdapter
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [FriendListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val accountViewModel: AccountViewModel by activityViewModels { viewModelFactory }
    private lateinit var friendsRecyclerAdapter: FriendsRecyclerAdapter

    private var _binding: FragmentFriendListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        friendsRecyclerAdapter = FriendsRecyclerAdapter(ArrayList())
        binding.recyclerView.adapter = friendsRecyclerAdapter

//        accountViewModel.updateFriends()
        accountViewModel.friends.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            friendsRecyclerAdapter.add(it)
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
         * @return A new instance of fragment FriendListFragment.
         */
        @JvmStatic
        fun newInstance() = FriendListFragment()
    }
}