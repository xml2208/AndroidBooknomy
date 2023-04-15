package com.example.androidbooknomy.ui.feature.main.main_app.home.payment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbooknomy.R
import kotlinx.coroutines.delay

@Composable
fun FactsInNumber(
) {
    var currentNumber by rememberSaveable { mutableStateOf(0) }

//    val currentNumber by produceState(initialValue = 0, producer = {
//        if (value <= 180) {
//            delay(20L)
//            value++
//        }
//    })

    LaunchedEffect(key1 = currentNumber) {
        if (currentNumber < 180) {
            delay(20L)
            currentNumber++
        }
    }

    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_media_player),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(25.dp)
        ) {
            Text(
                text = stringResource(R.string.facts_in_numbers),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(30.dp),
            )
            Text(
                text = if (currentNumber <= 24) "$currentNumber/7" else "24/7",
                modifier = Modifier.padding(25.dp),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.chance_for_learning_any_time),
                modifier = Modifier.padding(25.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = "$currentNumber",
                modifier = Modifier.padding(25.dp),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.days_for_learning),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(25.dp),
                color = Color.White
            )
            Text(
                text = if (currentNumber <= 3) "$currentNumber" else "3",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.listening_hours_in_day),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(25.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun ExampleCanvas() {
    Canvas(
        modifier = Modifier
            .background(Color.Red)
            .size(100.dp)
    ) {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path, Color.White)
    }
}

@Composable
fun ExampleBezier() {
    Canvas(
        modifier = Modifier
            .background(Color.Red)
            .size(200.dp)
    ) {
        val path = Path().apply {
            cubicTo(
                x1 = 0F, y1 = size.height,
                x2 = size.width.times(-0.2F), y2 = size.height.times(1.1F),
                x3 = size.width, y3 = size.height.times(0.8F)
            )
            lineTo(size.width, 0f)
            close()
        }
        drawPath(path, Color.White)
    }
}

@Composable
fun ExampleBezier2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .drawBehind {
                val path = Path().apply {
                    cubicTo(
                        x1 = 0F, y1 = size.height.times(1.1f),
                        x2 = size.width.times(-0.32F), y2 = size.height.times(0.95F),
                        x3 = size.width, y3 = size.height.times(0.85F)
                    )
                    lineTo(size.width, 0f)
                    close()
                }
                drawPath(path, Color.White)
            }
    ) {

    }
}

@Preview(heightDp = 700, widthDp = 400)
@Composable
fun ExampleCanvasPrev() {
    ExampleBezier2()
}

@Preview
@Composable
fun ExampleBezierPrev() {
    ExampleBezier()
}