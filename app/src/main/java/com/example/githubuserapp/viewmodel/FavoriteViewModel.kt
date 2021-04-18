package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.local.FavoriteDatabase
import com.example.githubuserapp.local.UserFavorite
import com.example.githubuserapp.local.UserFavoriteDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteUserDao: UserFavoriteDao?
    private var favoriteDb: FavoriteDatabase?

    init{
        favoriteDb = FavoriteDatabase.getDatabase(application)
        favoriteUserDao = favoriteDb?.userFavoriteDao()
    }

    fun getFavUser(): LiveData<List<UserFavorite>>? {
        return favoriteUserDao?.getUserFavorite()
    }
}