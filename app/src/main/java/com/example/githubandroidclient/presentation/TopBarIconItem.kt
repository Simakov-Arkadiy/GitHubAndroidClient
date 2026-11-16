package com.example.githubandroidclient.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TopBarIconItem(id: Int) {
    Icon(
        modifier = Modifier.size(40.dp),
        painter = painterResource(id),
        contentDescription = "Назад"
    )
}