package id.co.evolution.mygithubuserapps.feature.users.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.evolution.mygithubuserapps.core.util.Resource
import id.co.evolution.mygithubuserapps.feature.users.domain.usecase.GetUserDetailUseCase
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserDetailUiState
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    private val currentUsername: String?
        get() = savedStateHandle.get<String>("username")

    init {
        currentUsername?.let { username ->
            loadUserDetail(username)
        }
    }

    fun loadUserDetail(username: String, forceRefresh: Boolean = false) {
        getUserDetailUseCase(username, forceRefresh).onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, userDetail = result.data) }
                is Resource.Error -> _uiState.update { it.copy(isLoading = false, error = result.message) }
            }
        }.launchIn(viewModelScope)
    }

    fun retry() {
        currentUsername?.let { username ->
            loadUserDetail(username, forceRefresh = true)
        }
    }
}
