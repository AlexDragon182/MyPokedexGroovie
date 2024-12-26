package com.example.my_pokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.my_pokedex.database.entity.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class,
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun getEnsDao(): PokemonDao
}