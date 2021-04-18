package com.example.githubuserapp.view

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_PHOTO = "extra_photo"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_follower, R.string.tab_following)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.title = getString(R.string.detail_title)

        detailViewModelConfig()


    }

    private fun detailViewModelConfig() {
        val usernames = intent.getStringExtra(EXTRA_DATA)
        val id = intent.getIntExtra(EXTRA_USER_ID, 0)
        val photo = intent.getStringExtra(EXTRA_PHOTO)

        val bundle = Bundle()
        bundle.putString(EXTRA_DATA, usernames)
        showLoading(true)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(usernames)
        detailViewModel.getDetailUsers().observe(this, { userItems ->
            if (userItems != null) {
                binding.apply {
                    Glide.with(root.context)
                            .load(userItems.avatarUrl)
                            .apply(RequestOptions().override(150, 150))
                            .into(detailUserPic)
                    detailTvUsernames.text = userItems.login
                    detailFollowerTotal.text = userItems.followers.toString()
                    detailFollowingTotal.text = userItems.following.toString()
                    detailTvCompany.text = userItems.company
                    detailTvUserName.text = userItems.name
                    detailTvLocation.text = userItems.location
                    repository.text = String.format("Repository: %s", userItems.publicRepos)

                    showLoading(false)
                }
            }
        })
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val countUser = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (countUser != null){
                    if(countUser > 0){
                        binding.favButton.isChecked = true
                        _isChecked = true
                    } else {
                        binding.favButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.favButton.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                    detailViewModel.addToFav(usernames, id, photo)
            }else {
                detailViewModel.deleteFavorite(id)
            }
            binding.favButton.isChecked = _isChecked
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tabLayout, position ->
                tabLayout.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}