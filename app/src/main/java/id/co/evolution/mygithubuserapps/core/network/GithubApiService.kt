package id.co.evolution.mygithubuserapps.core.network

import id.co.evolution.mygithubuserapps.feature.users.data.remote.dto.SearchUserResponseDto
import id.co.evolution.mygithubuserapps.feature.users.data.remote.dto.UserDetailDto
import id.co.evolution.mygithubuserapps.feature.users.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): SearchUserResponseDto

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailDto
}