package id.co.evolution.mygithubuserapps.feature.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.usecase.SearchUsersUseCase
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiEvent
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiState
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    private var originalUsers: List<GithubUser> = emptyList()

    init {
        fetchUsers("user")
    }

    fun onEvent(event: UserListUiEvent) {
        when (event) {
            is UserListUiEvent.OnSearchQueryChange -> {
                val query = event.query
                val filteredList = if (query.isBlank()) {
                    originalUsers
                } else {
                    originalUsers.filter { user ->
                        user.username.contains(query, ignoreCase = true)
                    }
                }
                _uiState.update {
                    it.copy(
                        searchQuery = query,
                        users = filteredList
                    )
                }
            }
            is UserListUiEvent.OnSearchClick -> {
                fetchUsers(_uiState.value.searchQuery)
            }
            is UserListUiEvent.OnRetry -> {
                fetchUsers(_uiState.value.searchQuery, forceRefresh = true)
            }
            is UserListUiEvent.OnRefresh -> {
                fetchUsers(_uiState.value.searchQuery, forceRefresh = true)
            }
            is UserListUiEvent.OnToggleTheme -> {
                _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
            }
        }
    }

    private fun fetchUsers(query: String, forceRefresh: Boolean = false) {
        searchUsersUseCase(query, forceRefresh).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                is Resource.Success -> {
                    val fetchedUsers = result.data.orEmpty()
                    originalUsers = fetchedUsers
                    _uiState.update {
                        it.copy(isLoading = false, users = fetchedUsers, error = null)
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
