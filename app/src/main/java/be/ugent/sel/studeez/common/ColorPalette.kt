package be.ugent.sel.studeez.common

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

// Contains all colours that are used in the app. Currenlty, only light-theme colours are available.
// Reference colour palette: https://xd.adobe.com/view/3cb1e6ff-eb42-4a74-886e-7739c2ccc5ed-69e2/
// Use colours by calling (for example)
// ColorPalette.HIGH_EMPHASIS.onPrimary

// TODO Delete this class as we are now using ui.theme.Theme
class ColorPalette {
    companion object {
        // Use High emphasis colours if the thing is important.
        val HIGH_EMPHASIS: Colors = Colors(
            isLight = true,
            primary =           Color( 30, 100, 200, 255),
            primaryVariant =    Color( 27,  90, 180, 255),
            secondary =         Color(255, 210,   0, 255),
            secondaryVariant =  Color(253, 217,  49, 255),
            background =        Color.White,
            surface =           Color.White,
            error =             Color(176,   0,  32, 255),
            onPrimary =         Color.White,
            onSecondary =       Color.Black,
            onBackground =      Color.Black,
            onSurface =         Color.Black,
            onError =           Color.White
        )

        // Use medium emphasis colours if the thing is less important
        // or when another thing is selected while this one is not.
        val MEDIUM_EMPHASIS: Colors = Colors(
            isLight = true,
            primary =           Color( 30, 100, 200, 188),
            primaryVariant =    Color( 27,  90, 180, 188),
            secondary =         Color(255, 210,   0, 188),
            secondaryVariant =  Color(253, 217,  49, 188),
            background =        Color(255, 255, 255, 188),
            surface =           Color(255, 255, 255, 188),
            error =             Color(176,   0,  32, 188),
            onPrimary =         Color(255, 255, 255, 188),
            onSecondary =       Color(  0,   0,   0, 188),
            onBackground =      Color(  0,   0,   0, 188),
            onSurface =         Color(  0,   0,   0, 188),
            onError =           Color(255, 255, 255, 188)
        )

        // Use disabled colours if the thing is disabled, probably a button.
        val DISABLED: Colors = Colors(
            isLight = true,
            primary =           Color( 30, 100, 200,  97),
            primaryVariant =    Color( 27,  90, 180,  97),
            secondary =         Color(255, 210,   0,  97),
            secondaryVariant =  Color(253, 217,  49,  97),
            background =        Color(255, 255, 255,  97),
            surface =           Color(255, 255, 255,  97),
            error =             Color(176,   0,  32,  97),
            onPrimary =         Color(255, 255, 255,  97),
            onSecondary =       Color(  0,   0,   0,  97),
            onBackground =      Color(  0,   0,   0,  97),
            onSurface =         Color(  0,   0,   0,  97),
            onError =           Color(255, 255, 255,  97)
        )

        // Experimental function to darken colours if needed.
        fun darken(color: Color, amount: Float): Color {
            return Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), amount))
        }

        // Experimental function to lighten colours if needed.
        fun lighten(color: Color, amount: Float): Color {
            return Color(ColorUtils.blendARGB(color.toArgb(), Color.White.toArgb(), amount))
        }
    }
}