package com.example.githubuserapp.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.model.Users
import com.example.githubuserapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private val list = ArrayList<Users>()
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.main_title)
        
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        showUsersList()
        viewModelConfig()
    }

    private fun viewModelConfig() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.setAllUser()
        mainViewModel.getUsersAll().observe(this, { userItems ->
            if (userItems != null) {
                showLoading(false)
                adapter.setData(userItems)
            } else {
                Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showSelectedUser(user: Users) {

        val intentDetailPage = Intent(this@MainActivity, UserDetailActivity::class.java)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_DATA, user.usernames)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_PHOTO, user.photo)
        intentDetailPage.putExtra(UserDetailActivity.EXTRA_USER_ID, user.id)

        startActivity(intentDetailPage)
        Toast.makeText(this, "${user.usernames}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                list.clear()
                mainViewModel.setUserSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.fav_menu -> {
                val fIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(fIntent)
            }
            R.id.settings_reminder -> {
                val intent = Intent(this, SettingsReminderActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showUsersList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        adapter = ListUserAdapter()
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)
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

}