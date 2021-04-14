package com.example.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val list = ArrayList<Users>()
    }

//    private var list: ArrayList<Users> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.main_title)

        binding.rvUsers.setHasFixedSize(true)
        showUsersList()

    }

    private fun showSelectedUser(user:Users){
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()

        val data = Users(
                user.name,
                user.company,
                user.userLocation,
                user.followers,
                user.following,
                user.photo,
                user.usernames,
                user.repo
        )
        val intentDetailPage = Intent(this, UserDetailActivity::class.java)

        intentDetailPage.putExtra(UserDetailActivity.EXTRA_DATA, data)

        startActivity(intentDetailPage)
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
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getUsersSearch(newText)
                return true
            }
        })
        return true
    }

    private fun showUsersList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listUserAdapter

        getUsersList()

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)
            }
        })
    }

//    private fun getDataFromApi(username: String){
//        val client = AsyncHttpClient()
//        val url = "https://api.github.com/search/users?q=$username"
//        client.addHeader("Authorization", "token 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")
//        client.addHeader("User-Agent", "request")
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
//                val result = String(responseBody)
//            }
//
//            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

//    private fun getFollowers(url: String) {
//        val client = AsyncHttpClient()
//        client.addHeader("Authorization", "token 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")
//        client.addHeader("User-Agent", "request")
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray){
//                val result = String(responseBody)
//
//                try {
//                    val responseObject = JSONObject(result)
//
//                    val followerTotal = responseObject.getInt("followers")
//                    val followingTotal = responseObject.getInt("following")
//                    val company = responseObject.getString("company")
//                    val location = responseObject.getString("location")
//                    val user = Users()
//                    user.followers = followerTotal.toString()
//                    user.following= followingTotal.toString()
//                    user.company = company
//                    user.userLocation = location
//                    list.add(user)
//                } catch (e:Exception){
//                    e.printStackTrace()
//                }
//
//
//
//
//            }
//
//            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
//                binding.progressBar.visibility = View.INVISIBLE
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error.message}"
//                }
//                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun getUsersList() {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=ram"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
               binding.progressBar.visibility = View.INVISIBLE
               val result = String(responseBody)
               try {
                   val responseObject = JSONObject(result)
                   val items = responseObject.getJSONArray("items")

                   for (i in 0 until items.length()) {
                       val item = items.getJSONObject(i)
                       val username = item.getString("login")
                       val avatar = item.getString("avatar_url")
                       val htmlUrl = item.getString("html_url")
                       val user = Users()
                       user.usernames = username
                       user.photo = avatar
                       user.link = htmlUrl
                       list.add(user)

                   }
               } catch (e:Exception){
                   e.printStackTrace()
               }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUsersSearch(username: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val htmlUrl = item.getString("html_url")
                        val user = Users()
                        user.usernames = username
                        user.photo = avatar
                        user.link = htmlUrl
                        list.add(user)

                    }
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


}