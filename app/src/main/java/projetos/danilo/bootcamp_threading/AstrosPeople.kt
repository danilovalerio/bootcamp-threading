package projetos.danilo.bootcamp_threading

import com.google.gson.annotations.SerializedName

/**
 * Model astronauta
 */

data class AstrosPeople(
    @SerializedName("craft")
    val craft: String,
    @SerializedName("name")
    val name: String

)