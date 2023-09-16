package mx.webfamous.erix.mvi_demo.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

abstract class MVIBaseViewModel<S: State, E: Event> : BaseViewModel()  {
    // Event
    private val intents: Channel<E> = Channel(Channel.UNLIMITED)

    // State
    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState()) }
    val uiState get() = _uiState

    init { this.intentHandler() }

    abstract fun initialState(): S
    abstract fun intentHandler()

    fun eventHandler(intent: E) { execute { intents.send(intent) } }

    protected fun updateUi(
        handler: suspend (state: S) -> S
    ) = execute {
        _uiState.tryEmit(handler(_uiState.value))
    }

    protected fun executeIntent(
        action: suspend (E) -> Unit
    ) = execute {
        intents.consumeAsFlow().collect { action(it) }
    }
}

interface State
interface Event
