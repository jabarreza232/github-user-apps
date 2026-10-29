package id.co.evolution.mygithubuserapps.feature.users.domain.repository

import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(forceRefresh: Boolean = false): Flow<Resource<List<GithubUser>>>
    fun searchUsers(query: String): Flow<Resource<List<GithubUser>>>
    fun getUserDetail(username: String, forceRefresh: Boolean = false): Flow<Resource<GithubUserDetail>>
}
