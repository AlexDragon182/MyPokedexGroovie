package com.example.my_pokedex.networking

import com.example.my_pokedex.features.PokemonDetails.data.PokemonDetailsDto
import com.example.my_pokedex.features.pokedex.data.PokemonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPokedex {

    @GET("pokemon/")
    suspend fun getPokemon(
        @Query(value = "limit", encoded = true) limit: Int,
        @Query(value = "offset", encoded = true) offset: Int
    ): Response<PokemonDto>


    @GET("pokemon/{pokemon_name}")
    suspend fun getPokemonByName(
        @Path(value = "pokemon_name", encoded = true) pokemonName: String? = ""
    ): Response<PokemonDetailsDto>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

}