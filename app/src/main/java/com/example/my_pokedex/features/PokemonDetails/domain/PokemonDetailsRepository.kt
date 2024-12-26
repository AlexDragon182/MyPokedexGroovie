package com.example.my_pokedex.features.PokemonDetails.domain

import com.example.my_pokedex.utils.Resource

interface PokemonDetailsRepository {
    suspend fun getPokemonByName(name:String): Resource<PokemonDetailsModel>
}