package mx.webfamous.erix.mvi_demo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {
    protected fun execute(
        dispatcher: CoroutineDispatcher = Dispatchers.Main, //appDispatchers.mainDispatcher(),
        action: suspend () -> Unit
    ) = viewModelScope.launch(dispatcher) { action() }
}