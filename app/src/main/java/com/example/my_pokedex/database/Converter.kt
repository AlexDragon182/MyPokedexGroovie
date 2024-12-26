package com.example.my_pokedex.database

import androidx.room.TypeConverter
import com.example.my_pokedex.features.PokemonDetails.data.Abilities
import com.example.my_pokedex.features.PokemonDetails.data.Sprites
import com.example.my_pokedex.features.PokemonDetails.data.Stats
import com.example.my_pokedex.features.PokemonDetails.data.Types
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun stringToSprites(value: String): Sprites {
        val type: Type = object : TypeToken<Sprites?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun spritesToString(value: Sprites): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToAbilities(value: String): List<Abilities> {
        val type: Type = object : TypeToken<List<Abilities>>() {}.getType();
        return Gson().fromJson(value, type);
    }

    @TypeConverter
    fun abilitiesToString(value: List<Abilities>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Abilities?>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToStats(value: String): List<Stats> {
        val type: Type = object : TypeToken<List<Stats>>() {}.getType();
        return Gson().fromJson(value, type);
    }

    @TypeConverter
    fun statsToString(value: List<Stats>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Stats?>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToTypes(value: String): List<Types> {
        val type: Type = object : TypeToken<List<Types>>() {}.getType();
        return Gson().fromJson(value, type);
    }

    @TypeConverter
    fun typesToString(value: List<Types>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Types?>?>() {}.type
        return gson.toJson(value, type)
    }

}
