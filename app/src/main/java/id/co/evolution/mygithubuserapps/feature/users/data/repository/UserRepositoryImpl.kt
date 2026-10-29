package id.co.evolution.mygithubuserapps.feature.users.data.repository

import id.co.evolution.mygithubuserapps.core.network.GithubApiService
import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.data.local.UserDao
import id.co.evolution.mygithubuserapps.feature.users.data.mapper.toDetailDomain
import id.co.evolution.mygithubuserapps.feature.users.data.mapper.toDomain
import id.co.evolution.mygithubuserapps.feature.users.data.mapper.toEntity
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApiService,
    private val dao: UserDao
) : UserRepository {

    override fun getUsers(forceRefresh: Boolean): Flow<Resource<List<GithubUser>>> = flow {
        emit(Resource.Loading())

        val isLocalEmpty = dao.getUserCount() == 0
        val shouldFetchFromNetwork = isLocalEmpty || forceRefresh

        if (shouldFetchFromNetwork) {
            try {
                val remoteUsers = api.getUsers()
                dao.insertUsers(remoteUsers.map { it.toEntity() })
            } catch (e: Exception) {
                if (isLocalEmpty) {
                    emit(Resource.Error(e.localizedMessage ?: "Gagal memuat data"))
                    return@flow
                }
            }
        }

        emitAll(
            dao.observeUsers().map { entities ->
                Resource.Success(entities.map { it.toDomain() })
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun searchUsers(query: String): Flow<Resource<List<GithubUser>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchUsers(query)
            val entities = response.items.map { it.toEntity() }
            dao.insertUsers(entities)
            emit(Resource.Success(entities.map { it.toDomain() }))
        } catch (e: Exception) {
            val localResults = dao.searchUsersLocal(query).map { it.toDomain() }
            if (localResults.isNotEmpty()) {
                emit(Resource.Success(localResults))
            } else {
                emit(Resource.Error("Offline: User tidak ditemukan di cache lokal"))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserDetail(username: String, forceRefresh: Boolean): Flow<Resource<GithubUserDetail>> = flow {
        emit(Resource.Loading())

        val localUser = dao.getUserByUsername(username)
        if (localUser?.followers != null && !forceRefresh) {
            emit(Resource.Success(localUser.toDetailDomain()))
            return@flow
        }

        try {
            val remoteDetail = api.getUserDetail(username)
            val entity = remoteDetail.toEntity()
            dao.insertUser(entity)
            emit(Resource.Success(entity.toDetailDomain()))
        } catch (e: Exception) {
            if (localUser != null) {
                emit(Resource.Success(localUser.toDetailDomain()))
            } else {
                emit(Resource.Error(e.localizedMessage ?: "Gagal memuat detail user"))
            }
        }
    }.flowOn(Dispatchers.IO)
}