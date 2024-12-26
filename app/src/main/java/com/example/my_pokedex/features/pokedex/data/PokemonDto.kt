package com.example.my_pokedex.features.pokedex.data

import com.example.my_pokedex.features.pokedex.domain.PokemonModel
import com.example.my_pokedex.features.pokedex.domain.PokemonResultModel
import com.google.gson.annotations.SerializedName

data class PokemonDto (
    @SerializedName("count") var count: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("results") var results: List<PokemonResult> = listOf()
)

data class PokemonResult(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = ""
)

fun PokemonDto.toPokemonModel() = PokemonModel(
    results = results.map { it.toPokemonResultModel() }
)

fun PokemonResult.toPokemonResultModel() = PokemonResultModel(
    name = name
)