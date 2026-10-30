package id.co.evolution.mygithubuserapps.feature.users.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.co.evolution.mygithubuserapps.feature.users.presentation.component.GithubTopAppBar
import id.co.evolution.mygithubuserapps.feature.users.presentation.component.UserCard
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiEvent
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserListUiState

@Composable
fun UserListScreen(
    uiState: UserListUiState,
    onEvent: (UserListUiEvent) -> Unit,
    onUserClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            GithubTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "GitHub Users",
                actions = {
                    IconButton(onClick = { onEvent(UserListUiEvent.OnToggleTheme) }) {
                        Icon(
                            imageVector = if (uiState.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Switch Mode"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        UserListContent(
            uiState = uiState,
            onEvent = onEvent,
            onUserClick = onUserClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun UserListContent(
    uiState: UserListUiState,
    onEvent: (UserListUiEvent) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Professional Search Bar Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { onEvent(UserListUiEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Cari username GitHub...") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            onEvent(UserListUiEvent.OnSearchQueryChange(""))
                            onEvent(UserListUiEvent.OnSearchClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onEvent(UserListUiEvent.OnSearchClick) }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { onEvent(UserListUiEvent.OnSearchClick) },
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Cari"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onEvent(UserListUiEvent.OnRetry) },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Coba Lagi")
                        }
                    }
                }

                uiState.users.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonSearch,
                            contentDescription = null,
                            modifier = Modifier.size(72.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Pengguna Tidak Ditemukan",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Coba ketik kata kunci atau username lain di kolom pencarian",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = uiState.users,
                            key = { user -> user.id }
                        ) { user ->
                            UserCard(
                                user = user,
                                onUserClick = onUserClick
                            )
                        }
                    }
                }
            }
        }
    }
}
