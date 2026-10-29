package id.co.evolution.mygithubuserapps.feature.users.presentation.state

import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail

data class UserDetailUiState(
    val isLoading: Boolean = false,
    val userDetail: GithubUserDetail? = null,
    val error: String? = null
)