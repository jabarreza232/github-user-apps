package id.co.evolution.mygithubuserapps.feature.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDetailDto(
    @SerializedName("login") val login: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
    @SerializedName("public_repos") val publicRepos: Int?
)