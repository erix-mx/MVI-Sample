package mx.webfamous.erix.mvi_demo.counter.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

import mx.webfamous.erix.mvi_demo.counter.event.CounterEvent
import mx.webfamous.erix.mvi_demo.counter.state.CounterState

// ViewModel Composable
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            viewModel.eventHandler(CounterEvent.Increment)
        }
    }

    CounterScreenContent(
        state = state,
        onEvent = viewModel::eventHandler
    )
}

// UI Composable
@Composable
fun CounterScreenContent(
    state: CounterState,
    onEvent: (CounterEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onEvent(CounterEvent.Reset)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.counter.toString(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 128.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    CounterScreenContent(
        state = CounterState(counter = 10),
        onEvent = {}
    )
}
