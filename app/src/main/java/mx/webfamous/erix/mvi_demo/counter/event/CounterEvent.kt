package mx.webfamous.erix.mvi_demo.counter.event

import mx.webfamous.erix.mvi_demo.utils.Event

sealed class CounterEvent: Event {
    object Increment : CounterEvent()
    object Decrement : CounterEvent()
    object Reset : CounterEvent()
}