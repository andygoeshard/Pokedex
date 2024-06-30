package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Pokedex(
    @SerialName("count")
    val count: Int = 0,
    @SerialName("next")
    val next: String = "",
    @SerialName("previous")
    val previous: String = "none",
    @SerialName("results")
    val results: List<PokedexResults> = emptyList()
)