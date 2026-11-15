package com.example.githubandroidclient.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubandroidclient.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun RepositoriesListScreen(viewModel: RepositoriesListViewModel, navController: NavController) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is RepositoriesListViewModel.Action.RouteToAuthScreen -> navController.navigate("AuthScreen")
                is RepositoriesListViewModel.Action.RouteToDetailInfoScreen -> navController.navigate(
                    "DetailInfoScreen"
                )
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Your repositories", fontSize = 30.sp)
                IconButton(onClick = viewModel::logOutButtonPressed) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Выйти"
                    )
                }
            }
            when (screenState) {
                is RepositoriesListViewModel.ScreenState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                    }
                }

                is RepositoriesListViewModel.ScreenState.Loaded -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        itemsIndexed((screenState as RepositoriesListViewModel.ScreenState.Loaded).repos) { index, repo ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = {viewModel.clickableRowPressed(repo.id.toString())})
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = repo.name,
                                            color = colorResource(R.color.headingText),
                                            fontSize = 20.sp
                                        )
                                        Text(
                                            text = repo.language ?: "",
                                            color = colorResource(R.color.languageText),
                                            fontSize = 15.sp
                                        )
                                    }
                                    Text(repo.description ?: "")
                                }
                                if (index < (screenState as RepositoriesListViewModel.ScreenState.Loaded).repos.size - 1) {
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                    )
                                }
                            }
                        }
                    }
                }

                is RepositoriesListViewModel.ScreenState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text((screenState as RepositoriesListViewModel.ScreenState.Error).error)
                    }
                }

                is RepositoriesListViewModel.ScreenState.Empty -> Text("")
            }
        }
    }
}