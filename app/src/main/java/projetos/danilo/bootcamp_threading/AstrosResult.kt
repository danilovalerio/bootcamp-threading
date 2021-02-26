package projetos.danilo.bootcamp_threading

import com.google.gson.annotations.SerializedName

/**
 * Classe para representar o resultado da api
 */

data class AstrosResult(
    @SerializedName("message") val message: String,
    @SerializedName("number") val number: Int,
    @SerializedName("people") val people: List<AstrosPeople>
)