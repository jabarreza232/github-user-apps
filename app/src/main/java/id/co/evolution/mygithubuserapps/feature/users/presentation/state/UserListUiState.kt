package id.co.evolution.mygithubuserapps.feature.users.presentation.state

import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser

data class UserListUiState(
    val isLoading: Boolean = false,
    val users: List<GithubUser> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)