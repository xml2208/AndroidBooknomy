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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
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
                AppTopScreen(background = R.drawable.bg_splash) {
                    Column {
                        MainPaymentPart(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                                .background(Color.White)
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            bookModel = bookModel
                        )
                        FactsInNumber()
                    }
                }
            }
        }
    }

    @Composable
    fun MainPaymentPart(
        modifier: Modifier,
        bookModel: BookModel
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
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = "Audio kurs narxi:")
                Text(
                    textDecoration = TextDecoration.LineThrough,
                    text = bookModel.price,
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = "40% chegirmada:")
                Text(
                    text = bookModel.salePrice.dropLast(2),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomText(content = "Tejab qolasiz:")
                Text(text = bookModel.economyPrice, color = Color.Gray)
            }
            Row(Modifier.padding(15.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onPayButtonClicked() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 45.dp)
                ) {
                    Text(text = "Sotib olish")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
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