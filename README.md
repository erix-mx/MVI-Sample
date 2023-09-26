# Implementación del Patrón MVI en Android Compose

Este repositorio contiene una elegante implementación del patrón Model-View-Intent (MVI) usando Android Compose, manifestada a través de un contador simple pero eficiente.

## Estructura Fundamental

Se ha trabajado meticulosamente en una estructura MVI modular, consistente en ViewModel, Estado y Evento para garantizar un flujo coherente y modularizado.

### `BaseViewModel.kt`

Es una clase base que proporciona una capa de abstracción para la ejecución de coroutines en el ámbito de un ViewModel.

```kotlin
open class BaseViewModel: ViewModel() {
    protected fun execute(
        dispatcher: CoroutineDispatcher = Dispatchers.Main, //appDispatchers.mainDispatcher(),
        action: suspend () -> Unit
    ) = viewModelScope.launch(dispatcher) { action() }
}
```

### `MVIBaseViewModel.kt`

Esta clase abstracta articula y estructura la arquitectura MVI en un ViewModel, responsabilizándose de la gestión y flujo de estados y eventos.

```kotlin
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
```

## Caso Práctico: Contador

![sample](https://firebasestorage.googleapis.com/v0/b/assets-1ae81.appspot.com/o/general%2Fezgif.com-video-to-gif-3.gif?alt=media&token=6bb4dd9b-4ad3-4ca1-851a-fa596ebca4e7**)


### `CounterViewModel`

Administra las operaciones primarias de un contador, permitiendo incrementar, decrementar y reiniciar el valor.

```kotlin
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
```

### `CounterScreen`

Es el composable principal que muestra el contador y gestiona la lógica subyacente para incrementarlo automáticamente.

```kotlin
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
```

Para obtener una vista previa de la representación del contador, se recomienda utilizar `CounterScreenPreview`.

## Reconocimientos

Extiendo mi sincero agradecimiento a [Rusvel Leyva](https://repleyva.netlify.app) por su invaluable contribución al enriquecer mi comprensión sobre el patrón MVI. Puede seguir sus insights en Twitter: [@repleyva](https://twitter.com/repleyva).
