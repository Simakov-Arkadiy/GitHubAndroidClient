package com.example.githubandroidclient.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.githubandroidclient.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    BoxWithConstraints(
        modifier = Modifier
            .background(color = colorResource(R.color.backGround))
            .fillMaxSize(1F),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = maxHeight * 0.12f)
                .fillMaxWidth(1f / 3f)
                .aspectRatio(1f)
                .background(
                    color = Color.White,
                    shape = CircleShape,
                )
                .padding(6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.github_invertocat_black),
                contentDescription = null,
                modifier = Modifier
                    .offset(y = -1.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        TextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = maxHeight * 0.35f),
            value = "",
            onValueChange = {},
            label = {
                Text(
                    text = "Personal access token",
                )
            },
        )
    }
    Box(modifier = Modifier
        .fillMaxSize(1f)
        .padding(bottom = 80.dp)
        .imePadding()){
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.8f)
                .height(50.dp),
        ) { Text("SIGN IN") }
    }
}