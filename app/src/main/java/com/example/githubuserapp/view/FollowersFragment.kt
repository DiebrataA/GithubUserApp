package com.example.githubuserapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var id: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        id = args?.getString(UserDetailActivity.EXTRA_DATA).toString()
        _binding = FragmentFollowersBinding.bind(view)


        binding.rvUsersFollower.layoutManager = LinearLayoutManager(context)
        binding.rvUsersFollower.setHasFixedSize(true)
        adapter = ListUserAdapter()
        binding.rvUsersFollower.adapter = adapter


        showLoading(true)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        detailViewModel.setUserFollowers(id)
        detailViewModel.getUserFollowers().observe(viewLifecycleOwner, { userFollower ->
            if (userFollower != null) {
                adapter.setData(userFollower)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}