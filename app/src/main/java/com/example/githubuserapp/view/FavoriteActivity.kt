package com.example.githubuserapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.local.UserFavorite
import com.example.githubuserapp.model.Users
import com.example.githubuserapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var favViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_page_title)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        viewModelInitialization()
        showUsersList()

    }

    private fun viewModelInitialization(){
        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.getFavUser()?.observe(this, { userItems ->
            if (userItems != null) {
                val listMap = mapList(userItems)
                adapter.setData(listMap)
            }
        })
    }

    private fun mapList(userItems: List<UserFavorite>): ArrayList<Users> {
        val listUser = ArrayList<Users>()
        for (userItems in userItems){
            val mapped = Users(
                userItems.username,
                userItems.id,
                userItems.avatar
            )
            listUser.add(mapped)
        }
        return listUser
    }

    private fun showSelectedUser(user: Users) {

        val intentDetailPage = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_DATA, user.usernames)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_PHOTO, user.photo)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_USER_ID, user.id)

        startActivity(intentDetailPage)
        Toast.makeText(this, "${user.usernames}", Toast.LENGTH_SHORT).show()
    }

    private fun showUsersList() {
        binding.rvUsersFav.layoutManager = LinearLayoutManager(this)
        binding.rvUsersFav.setHasFixedSize(true)
        binding.rvUsersFav.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)
            }
        })
    }
}