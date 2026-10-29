package id.co.evolution.mygithubuserapps.feature.users.domain.usecase

import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Resource<List<GithubUser>>> {
        return repository.getUsers(forceRefresh = forceRefresh)
    }
}
