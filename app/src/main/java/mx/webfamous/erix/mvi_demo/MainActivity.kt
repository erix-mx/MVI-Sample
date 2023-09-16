package mx.webfamous.erix.mvi_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import mx.webfamous.erix.mvi_demo.counter.screens.CounterScreen
import mx.webfamous.erix.mvi_demo.ui.theme.MVIDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIDemoTheme {
                // A surface container using the 'background' color from the theme
                CounterScreen()
            }
        }
    }
}
