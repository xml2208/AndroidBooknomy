package com.example.androidbooknomy.ui.feature.login

import android.support.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbooknomy.R
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.MainActivity
import com.example.androidbooknomy.utils.extension.replaceFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntryFragment() :
    ComposeFragment<RegistrationContract.State, RegistrationContract.Event, RegistrationContract.Effect>() {

    private val viewModel by viewModel<RegistrationViewModel>()
    override fun retrieveViewModel(): RegistrationViewModel = getViewModel()

    @Composable
    override fun FragmentContent() {
        EntryUi(
            background = R.drawable.bg_splash,
            topLogo = R.drawable.top_logo,
            register = { activity?.replaceFragment(RegisterFragment(), R.id.main_fragment_container) },
            moveToApp = { startActivity(MainActivity.getStartIntent(requireActivity()))}
        )
    }
}

@Composable
fun EntryUi(
    @DrawableRes background: Int,
    @DrawableRes topLogo: Int,
    register: () -> Unit,
    moveToApp: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.Crop

        )
        Image(
            painter = painterResource(topLogo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(270.dp)
                .padding(top = 15.dp, start = 20.dp, end = 20.dp)
        )
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = moveToApp,
                content = {
                    Text(
                        stringResource(R.string.ilovaga_otish),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20),
                border = BorderStroke(1.dp, Color.White),
                contentPadding = PaddingValues(horizontal = 40.dp)

            )
            Button(
                onClick = register,
                content = {
                    Text(
                        stringResource(R.string.kirish_btn),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                contentPadding = PaddingValues(horizontal = 20.dp)
            )
        }
    }
}

@Preview()
@Composable
fun EntryUiPreview() {
    EntryUi(R.drawable.bg_splash, R.drawable.top_logo, {}, {})
}