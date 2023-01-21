package com.example.androidbooknomy.ui.feature.main

import android.support.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidbooknomy.R

@Composable
fun AppTopScreen(@DrawableRes background: Int, column: @Composable () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.top_logo),
                contentDescription = null,
                modifier = Modifier.padding(
                    top = 100.dp,
                    bottom = 30.dp,
                    start = 55.dp,
                    end = 55.dp
                ),
                alignment = Alignment.TopCenter
            )
            column()
        }
    }
}