package com.example.my_pokedex.features.pokedex.domain

data class PokemonModel (
    var results: List<PokemonResultModel> = listOf()
)

data class PokemonResultModel(
    var name: String = "",
    var hasAlreadyBeenSeen : Boolean = false
)