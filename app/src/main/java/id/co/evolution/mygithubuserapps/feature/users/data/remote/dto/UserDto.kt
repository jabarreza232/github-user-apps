package id.co.evolution.mygithubuserapps.feature.users.data.remote.dto

import com.google.gson.annotations.SerializedName

//Data Transfer Object
data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)