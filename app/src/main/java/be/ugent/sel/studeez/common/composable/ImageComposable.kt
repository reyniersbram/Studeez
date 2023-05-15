package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ImageBackgroundButton(
    paint: Painter,
    str: String,
    background2: Color,
    setBackground1: (Color) -> Unit,
    setBackground2: (Color) -> Unit
) {
    Image(
        painter = paint,
        str,
        modifier = Modifier
            .clickable {
                if (background2 == Color.Transparent) {
                    setBackground1(Color.LightGray)
                    setBackground2(Color.Transparent)
                } else {
                    setBackground2(Color.Transparent)
                }
            }
            .border(
                width = 2.dp,
                color = background2,
                shape = RoundedCornerShape(16.dp)
            )
    )
}