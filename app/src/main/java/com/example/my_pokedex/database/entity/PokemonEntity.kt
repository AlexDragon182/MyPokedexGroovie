package com.example.my_pokedex.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_entity_table"
)
data class PokemonEntity(//Entity
    @PrimaryKey
    @NonNull
    var name: String
)