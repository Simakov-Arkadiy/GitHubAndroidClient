package com.example.githubandroidclient.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.githubandroidclient.R

@Composable
fun DetailInfoScreen(viewModel: RepositoryInfoViewModel, navController: NavController) {

    val uriHandler = LocalUriHandler.current
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is RepositoryInfoViewModel.DetailInfoScreenAction.RouteToAuthScreen -> navController.navigate(
                    "AuthScreen"
                )

                is RepositoryInfoViewModel.DetailInfoScreenAction.RouteToRepositoriesListScreen -> navController.navigate(
                    "RepositoriesListScreen"
                )
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        when (screenState) {
            is RepositoryInfoViewModel.DetailInfoScreenState.Loading ->

                Column(modifier = Modifier.fillMaxWidth()) {
                    val repo =
                        (screenState as RepositoryInfoViewModel.DetailInfoScreenState.Loaded).githubRepo
                    TopBarItem {
                        TopBarIconItem(R.drawable.backspace)
                        Text(
                            repo.name,
                            fontSize = 30.sp
                        )
                        TopBarIconItem(R.drawable.logout)
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                    }
                }

            is RepositoryInfoViewModel.DetailInfoScreenState.Loaded -> {
                val repo =
                    (screenState as RepositoryInfoViewModel.DetailInfoScreenState.Loaded).githubRepo
                val readmeState =
                    (screenState as RepositoryInfoViewModel.DetailInfoScreenState.Loaded).readmeState
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBarItem {
                        IconButton(onClick = viewModel::backButtonPressed) {
                            TopBarIconItem(R.drawable.backspace)
                        }
                        Text(
                            repo.name,
                            fontSize = 30.sp
                        )

                        IconButton(onClick = viewModel::logOutButtonPressed) {
                            TopBarIconItem(R.drawable.logout)
                        }
                    }
                    RowItem {
                        IconItem(R.drawable.link)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "github.com/${repo.owner.login}/${repo.name}",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    uriHandler.openUri("https://github.com/${repo.owner.login}/${repo.name}")
                                },
                            color = colorResource(R.color.headingText),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    RowItem {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = R.drawable.licence),
                                contentDescription = "",
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(stringResource(R.string.licence))
                        }
                        Text(
                            text = repo.license?.name ?: "No license",
                            textAlign = TextAlign.End,
                        )
                    }
                    RowItem {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconItem(R.drawable.star)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                repo.stargazersCount.toString(),
                                color = colorResource(R.color.starCount)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.stars))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconItem(R.drawable.call_split)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                repo.forksCount.toString(),
                                color = colorResource(R.color.forkCount)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.forks))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconItem(R.drawable.views)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                repo.watchersCount.toString(),
                                color = colorResource(R.color.watcherCount)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.watchers))
                        }
                    }
                    when (readmeState) {
                        is RepositoryInfoViewModel.ReadmeState.Loading -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(60.dp))
                        }

                        is RepositoryInfoViewModel.ReadmeState.Loaded -> Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) { Text(readmeState.markdown) }

                        is RepositoryInfoViewModel.ReadmeState.Error -> Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) { Text(stringResource(readmeState.error)) }

                        is RepositoryInfoViewModel.ReadmeState.Empty -> Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) { Text(stringResource(R.string.emptyReadme)) }
                    }
                }
            }
        }
    }
}