package id.co.evolution.mygithubuserapps.feature.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchUserResponseDto(
    @SerializedName("items") val items: List<UserDto>
)