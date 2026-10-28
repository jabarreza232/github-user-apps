package id.co.evolution.mygithubuserapps.feature.users.domain.usecase

import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<GithubUser>>> {
        if (query.isBlank()) return repository.getUsers()
        return repository.searchUsers(query)
    }
}