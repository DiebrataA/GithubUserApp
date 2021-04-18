package com.example.githubuserapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        id = args?.getString(UserDetailActivity.EXTRA_DATA).toString()
        _binding = FragmentFollowingBinding.bind(view)


        binding.rvUsersFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvUsersFollowing.setHasFixedSize(true)
        adapter = ListUserAdapter()
        binding.rvUsersFollowing.adapter = adapter


        showLoading(true)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        detailViewModel.setUserFollowing(id)
        detailViewModel.getUserFollowing().observe(viewLifecycleOwner, { userFollowing ->
            if (userFollowing != null) {
                adapter.setData(userFollowing)
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