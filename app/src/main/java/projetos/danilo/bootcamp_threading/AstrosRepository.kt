package projetos.danilo.bootcamp_threading

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class AstrosRepository {

    //Função para carregar os astronautas
    suspend fun loadData(): Deferred<List<AstrosPeople>> = withContext(Dispatchers.IO) {
        async {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://api.open-notify.org/astros.json")
                .build()

            val response = client.newCall(request).execute()
            Log.i("Teste", "Response: $response")
            val result = parseJsonToResult(response.body?.string())
            return@async result.people
        }
    }

    //TODO: Criar uma função util que receba o json e a classe model para a mesma
    //Função para converter o json
    fun parseJsonToResult(json: String?) =
        Gson().fromJson(json, AstrosResult::class.java)

}