package mx.webfamous.erix.mvi_demo.counter.screens

import dagger.hilt.android.lifecycle.HiltViewModel
import mx.webfamous.erix.mvi_demo.counter.event.CounterEvent
import mx.webfamous.erix.mvi_demo.counter.state.CounterState
import mx.webfamous.erix.mvi_demo.utils.MVIBaseViewModel
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor():
    MVIBaseViewModel<CounterState, CounterEvent>(){
    override fun initialState(): CounterState = CounterState(counter = 0)

    override fun intentHandler() {
        executeIntent { event ->
            when(event) {
                is CounterEvent.Increment -> increment()
                is CounterEvent.Decrement -> decrement()
                CounterEvent.Reset -> reset()
            }
        }
    }

    private fun reset() {
        updateUi { state ->
            state.copy(counter = 0)
        }
    }

    private fun increment() {
        updateUi { state ->
            state.copy(counter = state.counter + 1)
        }
    }

    private fun decrement() {
        updateUi { state ->
            state.copy(counter = state.counter - 1)
        }
    }
}