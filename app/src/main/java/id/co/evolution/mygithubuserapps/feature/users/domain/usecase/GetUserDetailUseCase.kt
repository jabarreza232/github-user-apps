package id.co.evolution.mygithubuserapps.feature.users.domain.usecase

import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(username: String, forceRefresh: Boolean = false): Flow<Resource<GithubUserDetail>> {
        return repository.getUserDetail(username, forceRefresh = forceRefresh)
    }
}
