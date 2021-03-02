package projetos.danilo.bootcamp_threading

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A ViewModel foi projetada para armazenar e gerenciar dados relacionados à interface
 * do usuário de maneira consciente do ciclo de vida.
 * A classe ViewModel permite que os dados sobrevivam a alterações na configuração,
 * por ex.: Ao girar a tela
 */
class MainViewModel : ViewModel(), CoroutineScope {
    private val state: MutableLiveData<MainState> = MutableLiveData()
    private val event: MutableLiveData<MainEvent> = MutableLiveData()

    val viewState: LiveData<MainState> = state
    val viewEvent: LiveData<MainEvent> = event

    //Uso de coroutines
    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun inicializar(isOnline: Boolean) {
        //state.postValue(MainState.MensagemBoasVindas("Bem vindo aqui é a ViewModel Jão $isOnline"))
        if (isOnline) {
            fetchAstrosPeoples()
        }  else {
            event.postValue(MainEvent.ShowConnectedError)
        }
    }

    private fun fetchAstrosPeoples() {
        event.value = MainEvent.HideConnectedError
        val repository = AstrosRepository()
        event.postValue(MainEvent.ShowLoading)
        launch {
            val response = repository.loadData()
            onFinishCall(response)
        }
    }

    private fun onFinishCall(response: Deferred<List<AstrosPeople>>) {
        if (response != null) {
            event.value = MainEvent.HideLoading
            val list: List<AstrosPeople> = response.getCompleted()
            state.postValue(MainState.ListaAstrosPeople(list))
        } else {
            state.postValue(
                MainState.ListaAstrosPeopleVazia(
                    "Lista vazia, não foi possível carregar."
                )
            )
        }
    }

    fun interpret(interactor: MainInteractor) {
        when (interactor) {
            is MainInteractor.ClickCarregar -> inicializar(true)
        }
    }
}