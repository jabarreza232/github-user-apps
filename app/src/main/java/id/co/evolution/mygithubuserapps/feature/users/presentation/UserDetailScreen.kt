package id.co.evolution.mygithubuserapps.feature.users.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import id.co.evolution.mygithubuserapps.feature.users.presentation.component.GithubTopAppBar
import id.co.evolution.mygithubuserapps.feature.users.presentation.state.UserDetailUiState
import id.co.evolution.mygithubuserapps.feature.users.presentation.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    onBackClick: () -> Unit = {},
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            GithubTopAppBar(
                title = uiState.userDetail?.username?.let { "@$it" } ?: "Detail User",
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        UserDetailContent(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onBackClick = onBackClick,
            onRetryClick = { viewModel.retry() }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserDetailContent(
    uiState: UserDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            uiState.error != null -> Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onRetryClick) {
                    Text("Coba Lagi")
                }
            }
            uiState.userDetail != null -> {
                val detail = uiState.userDetail
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GlideImage(
                        model = detail.avatarUrl,
                        contentDescription = detail.username,
                        modifier = Modifier.size(120.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = detail.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "@${detail.username}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = detail.bio, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Repos: ${detail.publicRepos}")
                        Text("Followers: ${detail.followers}")
                        Text("Following: ${detail.following}")
                    }
                }
            }
        }
    }
}
