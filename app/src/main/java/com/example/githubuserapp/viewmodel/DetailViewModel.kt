package com.example.githubuserapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.local.FavoriteDatabase
import com.example.githubuserapp.local.UserFavorite
import com.example.githubuserapp.local.UserFavoriteDao
import com.example.githubuserapp.model.UserDetails
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val userDetails = MutableLiveData<UserDetails>()

    private var favoriteUserDao: UserFavoriteDao?
    private var favoriteDb: FavoriteDatabase?

    init {
        favoriteDb = FavoriteDatabase.getDatabase(application)
        favoriteUserDao = favoriteDb?.userFavoriteDao()
    }

    fun setDetailUser(id: String?) {
        val client = AsyncHttpClient()
        val token = BuildConfig.GitHub_Token
        val url = "https://api.github.com/users/$id"
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)

                val item = JSONObject(result)
                val username = item.getString("login")
                val avatar = item.getString("avatar_url")
                val company = item.getString("company")
                val location = item.getString("location")
                val followers = item.getInt("followers")
                val following = item.getInt("following")
                val repo = item.getInt("public_repos")
                val name = item.getString("name")
                val followerUrl = item.getString("followers_url")
                val followingUrl = item.getString("following_url")
                val id = item.getInt("id")
                val user = UserDetails()

                user.followers = followers
                user.company = company
                user.location = location
                user.following = following
                user.publicRepos = repo
                user.login = username
                user.avatarUrl = avatar
                user.name = name
                user.followersUrl = followerUrl
                user.followingUrl = followingUrl
                user.id = id

                userDetails.postValue(user)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", errorMessage)
            }
        })
    }

    fun getDetailUsers(): LiveData<UserDetails> {
        return userDetails
    }

    fun addToFav(username: String?, id: Int, avatar: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var userFav = UserFavorite(username, id, avatar)
            favoriteUserDao?.addToUserFavorite(userFav)
        }
    }

    suspend fun checkUser(id: Int) = favoriteUserDao?.checkUserFavorite(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.deleteUserFavorite(id)
        }
    }
}