package mx.webfamous.erix.mvi_demo.counter.state

import mx.webfamous.erix.mvi_demo.utils.State

data class CounterState(
    val counter: Int = 0
): State