package projetos.danilo.bootcamp_threading

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter = AstrosPeopleAdapter()

    private val btnLoad by lazy { findViewById<Button>(R.id.btn_load_data) }
    private val pbLoadingData by lazy { findViewById<ProgressBar>(R.id.pb_loading_data) }
    private val msgConnected by lazy { findViewById<ConstraintLayout>(R.id.cl_no_connected) }
    private val rvAstrosPeople by lazy { findViewById<RecyclerView>(R.id.rv_astros_people) }


    //TODO: Transformar a lista de astronauta em um RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.inicializar(isConnected(baseContext))

        initObservers()

        btnLoad.setOnClickListener {
            viewModel.inicializar(isConnected(baseContext))
        }


    }

    private fun setupRecyclerView() {
        rvAstrosPeople.visibility = View.VISIBLE
        rvAstrosPeople.layoutManager = LinearLayoutManager(this)
        rvAstrosPeople.adapter = adapter
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
                    is MainEvent.ShowConnectedError -> showConnectError()
                    is MainEvent.HideConnectedError -> hideConnectError()
                }
            }

        })
    }

    private fun exibeBoasVindas(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    //Exibir os dados carregados
    private fun showDataLoad(list: List<AstrosPeople>?) {
        setupRecyclerView()
        if (list != null) {
            adapter.update(list)
        }
    }

    private fun showListaVazia(string: String) {
        adapter.update(mutableListOf())
    }

    //Exibir a ProgressBar
    private fun showLoadingIndicator() {
        pbLoadingData.visibility = View.VISIBLE
    }


    //Esconder a ProgressBar
    private fun hideLoadingIndicator() {
        pbLoadingData.visibility = View.GONE
    }

    //Quando estiver sem conexão de internet
    private fun showConnectError() {
        msgConnected.visibility = View.VISIBLE
        rvAstrosPeople.visibility = View.GONE
        hideLoadingIndicator()
        btnLoad.text = getString(R.string.btn_recarregar)
    }

    //Quando estiver sem conexão de internet
    private fun hideConnectError() {
        msgConnected.visibility = View.GONE
    }
}