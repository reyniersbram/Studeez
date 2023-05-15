package be.ugent.sel.studeez.common.ext

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.generateRandomArgb(): Long {
    val random = Random
    val mask: Long = (0x000000FFL shl random.nextInt(0, 3)).inv()
    return random.nextLong(0xFF000000L, 0xFFFFFFFFL) and mask
}