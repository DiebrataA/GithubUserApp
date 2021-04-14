package com.example.githubuserapp

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users (
        var link: String? = null,
        var name: String? = null,
        var company: String? = null,
        var userLocation: String? = null,
        var followers: String?= null,
        var following: String? =null,
        var photo: String? = null,
        var usernames: String? =null,
        var repo: String? =null
) : Parcelable