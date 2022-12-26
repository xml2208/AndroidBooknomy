package com.example.androidbooknomy.ui.feature.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbooknomy.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(goToEntryUi: () -> Unit) {

    val items = listOf(OnBoardingItem.Item1, OnBoardingItem.Item2)
    val pagerState = rememberPagerState()

    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_splash),
            contentDescription = "bg_splash",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.top_logo),
            contentDescription = "top_logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(35.dp)
        )
        HorizontalPager(
            count = items.size, modifier = Modifier.align(Center), state = rememberPagerState()
        ) {
            OnBoardingItemUi(onBoardingItem = items[pagerState.currentPage])
            if (pagerState.currentPage == 2) {
                goToEntryUi()
            }
        }

        HorizontalPagerIndicator(
            pagerState = rememberPagerState(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            activeColor = Color.White,
            inactiveColor = Color.Gray,
        )

    }
}

@Composable
fun OnBoardingItemUi(onBoardingItem: OnBoardingItem) {
    Column(modifier = Modifier.padding(30.dp)) {
        Image(
            painter = painterResource(id = onBoardingItem.image),
            contentDescription = null,
            modifier = Modifier
                .align(
                    CenterHorizontally
                )
                .width(60.dp)
        )
        Text(
            text = stringResource(id = onBoardingItem.content),
            fontSize = 30.sp,
            modifier = Modifier.align(CenterHorizontally),
            color = Color.White
        )
    }
}

sealed class OnBoardingItem(val image: Int, @StringRes val content: Int) {
    object Item1 : OnBoardingItem(image = R.drawable.bg_audio, R.string.onboarding_content1)
    object Item2 : OnBoardingItem(image = R.drawable.bg_audio, R.string.onboarding_content2)
//    companion object {
//        fun getOnBoardingItems(): List<OnBoardingItem> {
//            return listOf(
//                OnBoardingItem(R.drawable.bg_audio, R.string.onboarding_content1),
//                OnBoardingItem(R.drawable.bg_audio, R.string.onboarding_content2)
//            )
//        }
//    }
}

@Preview
@Composable
fun OnBoardingItemPrev() {
    OnBoardingScreen {

    }
}