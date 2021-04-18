package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.model.Users
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {
    val userFollowing = MutableLiveData<ArrayList<Users>>()

    fun setUserFollowing(id: String?) {
        val list = ArrayList<Users>()
        val client = AsyncHttpClient()
        val token = BuildConfig.GitHub_Token
        val url = "https://api.github.com/users/$id/following"
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d("hasil", result)

                try {
                    val items = JSONArray(result)

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
                    userFollowing.postValue(list)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
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

    fun getUserFollowing(): LiveData<ArrayList<Users>> {
        return userFollowing
    }
}