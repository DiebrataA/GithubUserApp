package com.example.githubuserapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.databinding.ActivityDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        val user = intent.getParcelableExtra<Users>(EXTRA_DATA) as Users

        with(user){
//            binding.detailUserPic.text(photo)
            binding.detailTvUserName.text = name
            binding.detailTvLocation.text = userLocation
            binding.detailTvCompany.text = company
            binding.detailFollowerTotal.text = followers
            binding.detailFollowingTotal.text = following
            binding.detailTvUsernames.text = usernames
            binding.repository.text = String.format("Repository: %s", repo)
        }


        actionBar?.title = getString(R.string.detail_title)
    }

}