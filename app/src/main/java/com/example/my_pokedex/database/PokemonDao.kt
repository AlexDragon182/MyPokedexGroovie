package com.example.my_pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.my_pokedex.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon_entity_table")
    fun getAllPokemon() : LiveData<List<PokemonEntity>>//po

}