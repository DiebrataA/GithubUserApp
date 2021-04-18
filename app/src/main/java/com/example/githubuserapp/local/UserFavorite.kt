package com.example.githubuserapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_favorite")
data class UserFavorite(
    val username: String? = null,
    @PrimaryKey
    val id: Int,
    val avatar: String? = null
    ) : Serializable
