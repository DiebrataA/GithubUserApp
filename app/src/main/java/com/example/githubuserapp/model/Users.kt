package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
        var usernames: String? = null,
        var id: Int? = null,
        var photo: String? = null,
        var company: String? = null,
        var userLocation: String? = null,
        var followers: String? = null,
        var following: String? = null,
        var name: String? = null,
        var repo: String? = null,
        var link: String? = null,
) : Parcelable