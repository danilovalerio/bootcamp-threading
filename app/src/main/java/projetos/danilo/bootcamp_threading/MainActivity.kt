package projetos.danilo.bootcamp_threading

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val btnLoad by lazy { findViewById<Button>(R.id.btn_load_data) }
    private val listAstronauts by lazy { findViewById<TextView>(R.id.tv_data) }
    private val pbLoadingData by lazy { findViewById<ProgressBar>(R.id.pb_loading_data) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()

        btnLoad.setOnClickListener {
//            viewModel.inicializar()
            //launchAstrosTask()
            viewModel.interpret(MainInteractor.ClickCarregar)
        }
    }

    private fun initObservers() {
        //state Observe
        viewModel.viewState.observe(this, Observer { viewState ->
            viewState?.let {
                when (it) {
                    is MainState.MensagemBoasVindas -> exibeBoasVindas(it.string)
                    is MainState.ListaAstrosPeople -> showDataLoad(it.listaAstrosPeople)
                    is MainState.ListaAstrosPeopleVazia -> showListaVazia(it.string)
                }
            }
        })

        //event Observe
        viewModel.viewEvent.observe(this, Observer { viewEvent ->
            viewEvent?.let {
                when (it) {
                    is MainEvent.HideLoading -> hideLoadingIndicator()
                    is MainEvent.ShowLoading -> showLoadingIndicator()
                }
            }

        })
    }

    fun exibeBoasVindas(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    //Exibir os dados carregados
    fun showDataLoad(list: List<AstrosPeople>?) {
        listAstronauts.text = ""

        list?.forEach { item ->
            listAstronauts.append("${item.name} - ${item.craft} \n\n")
        }
    }

    fun showListaVazia(string: String) {
        listAstronauts.text = string
    }


    //Exibir a ProgressBar
    fun showLoadingIndicator() {
        pbLoadingData.visibility = View.VISIBLE
    }


    //Esconder a ProgressBar
    fun hideLoadingIndicator() {
        pbLoadingData.visibility = View.GONE
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
            showLoadingIndicator()
        }

        override fun doInBackground(vararg p0: Void?): List<AstrosPeople> {
            return repository.loadData()
        }

        override fun onPostExecute(result: List<AstrosPeople>?) {
            super.onPostExecute(result)
            hideLoadingIndicator()
            showDataLoad(result)
        }

    }
}