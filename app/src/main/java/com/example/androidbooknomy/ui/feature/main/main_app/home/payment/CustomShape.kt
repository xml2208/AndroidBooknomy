package com.example.androidbooknomy.ui.feature.main.main_app.home.payment

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.graphics.Shape

class CustomShape : Shape {

    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ) = Outline.Generic(Path().apply {
        cubicTo(
            x1 = 0F, y1 = size.height.times(1.1f),
            x2 = size.width.times(-0.32F), y2 = size.height.times(0.95F),
            x3 = size.width, y3 = size.height.times(0.85F)
    )
        close()
    })
}

//val triangle =
//    moveTo(size.width / 2f, 0f)
//    lineTo(size.width, size.height)
//    lineTo(0f, size.height)
//    close()
