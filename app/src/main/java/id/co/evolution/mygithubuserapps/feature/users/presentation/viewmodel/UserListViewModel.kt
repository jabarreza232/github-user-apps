package id.co.evolution.mygithubuserapps.feature.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.usecase.SearchUsersUseCase
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiEvent
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiState
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    fun onEvent(event: UserListUiEvent) {
        when (event) {
            is UserListUiEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }
            is UserListUiEvent.OnRetry -> {
                fetchUsers(_uiState.value.searchQuery, forceRefresh = true)
            }
            is UserListUiEvent.OnRefresh -> {
                fetchUsers(_uiState.value.searchQuery, forceRefresh = true)
            }
        }
    }

    private fun observeSearchQuery() {
        searchQueryFlow
            .debounce(500L)
            .distinctUntilChanged()
            .onEach { query -> fetchUsers(query) }
            .launchIn(viewModelScope)
    }

    private fun fetchUsers(query: String, forceRefresh: Boolean = false) {
        searchUsersUseCase(query, forceRefresh).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, users = result.data.orEmpty(), error = null)
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
