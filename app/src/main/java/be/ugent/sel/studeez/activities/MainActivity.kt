package be.ugent.sel.studeez.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import be.ugent.sel.studeez.StudeezApp
import be.ugent.sel.studeez.screens.session.InvisibleSessionManager
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

var onTimerInvisible: Job? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudeezTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StudeezApp()
                }
            }
        }
    }



    override fun onStop() {
        onTimerInvisible = lifecycleScope.launch {
            InvisibleSessionManager.updateTimer()
        }
        super.onStop()
    }

    override fun onStart() {
        onTimerInvisible?.cancel()
        super.onStart()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudeezTheme {
        Greeting("Android")
    }
}