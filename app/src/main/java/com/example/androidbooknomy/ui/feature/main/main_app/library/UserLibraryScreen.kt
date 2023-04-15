package com.example.androidbooknomy.ui.feature.main.main_app.library

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.androidbooknomy.R
import com.example.androidbooknomy.utils.extension.handleBackPressedEvent
import com.github.terrakok.cicerone.Router
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserLibraryFragment : Fragment() {

    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            handleBackPressedEvent { router.exit() }
            UserLibraryScreen()
        }
    }
}

@Composable
fun UserLibraryScreen() {

    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_audio),
            modifier = Modifier.fillMaxSize(),
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
            ViewPagerPart()
            Column(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
            ) {
                Text(text = stringResource(R.string.my_library), fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
                Text(text = stringResource(R.string.no_books), fontWeight = FontWeight.Bold, modifier = Modifier.align(CenterHorizontally), fontSize = 25.sp)
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerPart() {
    val strList = listOf(
        stringResource(R.string.listen_and),
        stringResource(R.string.listen_and),
        stringResource(R.string.listen_and),
        stringResource(R.string.listen_and),
        stringResource(R.string.listen_and),
        stringResource(R.string.listen_and)
    )
    val viewPagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    scope.launch {
        delay(3000)
        viewPagerState.scrollToPage((viewPagerState.currentPage + 1 ) % (viewPagerState.pageCount))
    }

    HorizontalPager(
        state = viewPagerState,
        count = strList.size,
        modifier = Modifier
            .padding(vertical = 30.dp)
    ) {
            Text(
                text = strList[it],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
    }

@Preview(showSystemUi = true)
@Composable
fun UserLibraryScreenPrev() {
    UserLibraryScreen()
}