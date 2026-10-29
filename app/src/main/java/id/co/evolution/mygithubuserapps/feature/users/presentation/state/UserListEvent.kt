package id.co.evolution.mygithubuserapps.feature.users.presentation.state

sealed interface UserListUiEvent {
    data class OnSearchQueryChange(val query: String) : UserListUiEvent
    data object OnRetry : UserListUiEvent
    data object OnRefresh : UserListUiEvent
}
