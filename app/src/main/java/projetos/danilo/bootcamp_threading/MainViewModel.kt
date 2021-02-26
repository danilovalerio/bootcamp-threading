package projetos.danilo.bootcamp_threading

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * A ViewModel foi projetada para armazenar e gerenciar dados relacionados à interface
 * do usuário de maneira consciente do ciclo de vida.
 * A classe ViewModel permite que os dados sobrevivam a alterações na configuração,
 * por ex.: Ao girar a tela
 */
class MainViewModel : ViewModel() {
    private val state: MutableLiveData<MainState> = MutableLiveData()
    private val event: MutableLiveData<MainEvent> = MutableLiveData()

    val viewState: LiveData<MainState> = state
    val viewEvent: LiveData<MainEvent> = event

    fun inicializar() {
        state.postValue(MainState.MensagemBoasVindas("Bem vindo aqui é a ViewModel Jão"))
    }

    fun interpret(interactor: MainInteractor) {
        when (interactor) {
            is MainInteractor.ClickCarregar -> launchAstrosTask()
        }
    }

    //Função que lança a Task
    fun launchAstrosTask() {
        val task = TaskAstros()
        task.execute()
    }

    //classe interna para rodar a tarefa assincrona
    inner class TaskAstros() : AsyncTask<Void, Int, List<AstrosPeople>>() {
        val repository = AstrosRepository()

        override fun onPreExecute() {
            super.onPreExecute()
            event.postValue(MainEvent.ShowLoading)
        }

        override fun doInBackground(vararg p0: Void?): List<AstrosPeople> {
            return repository.loadData()
        }

        override fun onPostExecute(result: List<AstrosPeople>?) {
            super.onPostExecute(result)
            event.value = MainEvent.HideLoading
            if (result != null) {
                state.postValue(MainState.ListaAstrosPeople(result))
            } else {
                state.postValue(
                    MainState.ListaAstrosPeopleVazia(
                        "Lista vazia, não foi possível carregar."
                    )
                )
            }
        }

    }


}