package com.example.androidbooknomy.ui.feature.main.main_app.home.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.feature.main.AppTopScreen

const val BOOK_MODEL_ID = "book_model"
class PaymentScreenFragment : Fragment() {

    private val SERVICE_ID = "18673"
    private val MERCHANT_ID = "13335"

    private lateinit var bookModel: BookModel

    companion object {
        fun newInstance(bookModel: BookModel): PaymentScreenFragment {
            val args = Bundle().apply {
                putParcelable(BOOK_MODEL_ID, bookModel)
            }
            return PaymentScreenFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        bookModel = arguments?.getParcelable(BOOK_MODEL_ID)!!

        setContent {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                AppTopScreen(background = R.drawable.bg_media_player) {
                    Column {
                        MainPaymentPart(
                            bookModel = bookModel,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                                .background(color = Color.White, shape = CustomShape())
                                .drawBehind {
                                    val path = Path().apply {
                                        cubicTo(
                                            x1 = 0F,
                                            y1 = size.height,
                                            x2 = size.width.times(-0.3F),
                                            y2 = size.height.times(1.1F),
                                            x3 = size.width,
                                            y3 = size.height.times(0.8F)
                                        )
                                        lineTo(size.width, 0f)
                                        close()
                                    }
                                    drawPath(path, Color.White)
                                }
                                .padding(20.dp)
                        )
                        FactsInNumber()
                    }
                }
            }
        }
    }

    @Composable
    fun MainPaymentPart(
        bookModel: BookModel, modifier: Modifier
    ) {
        Column(modifier = modifier) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    fontWeight = FontWeight.Bold,
                    text = bookModel.title,
                    fontSize = 35.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Row {
                Spacer(modifier = Modifier.weight(1f))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.bg_media_player).data(bookModel.photo.photoUrl).build(),
                    modifier = Modifier
                        .size(width = 150.dp, height = 220.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = stringResource(R.string.cost_of_audio_course))
                Text(
                    textDecoration = TextDecoration.LineThrough,
                    text = bookModel.price,
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = stringResource(R.string.promotion_40_percent))
                Text(
                    text = bookModel.salePrice.dropLast(2),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = stringResource(R.string.economy_price))
                Text(text = bookModel.economyPrice, color = Color.Gray)
            }
            Row(Modifier.padding(bottom = 25.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onPayButtonClicked() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 45.dp)
                ) {
                    Text(text = stringResource(R.string.buy_button))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Box(modifier = Modifier.height(80.dp))
        }
    }

    @Composable
    fun CustomText(content: String) {
        Text(text = content, color = Color.Gray, fontSize = 18.sp)
    }

    private fun onPayButtonClicked() {
        val url =
            "https://my.click.uz/services/pay/?service_id=$SERVICE_ID&merchant_id=$MERCHANT_ID&amount=${
                bookModel.salePrice.dropLast(
                    2
                )
            }&transaction_param=${bookModel.id}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        requireContext().startActivity(intent)
    }
}