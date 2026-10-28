package id.co.evolution.mygithubuserapps.feature.users.data.repository

import id.co.evolution.mygithubuserapps.core.network.GithubApiService
import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.data.mapper.toDomain
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApiService
) : UserRepository {

    override fun getUsers(): Flow<Resource<List<GithubUser>>> = flow {
        emit(Resource.Loading())
        try {
            val users = api.getUsers().map { it.toDomain() }
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Terjadi kesalahan jaringan"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchUsers(query: String): Flow<Resource<List<GithubUser>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchUsers(query)
            emit(Resource.Success(response.items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Gagal mencari user"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserDetail(username: String): Flow<Resource<GithubUserDetail>> = flow {
        emit(Resource.Loading())
        try {
            val detail = api.getUserDetail(username).toDomain()
            emit(Resource.Success(detail))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Gagal memuat detail user"))
        }
    }.flowOn(Dispatchers.IO)
}