package com.example.my_pokedex.features.PokemonDetails.domain

import android.os.Parcelable
import com.example.my_pokedex.features.PokemonDetails.data.Sprites
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PokemonDetailsModel(
    var id: Int = 0,
    var name: String? = null,
    var height: Int? = null,
    var order: Int? = null,
    var weight: Int? = null,
    var sprites: SpritesModel? = SpritesModel(),
    var abilities: List<AbilitiesModel> = listOf(),
    var stats: List<StatsModel> = listOf(),
    var types: List<TypesModel> = listOf(),
): Parcelable

@Parcelize
data class AbilitiesModel(
    var isHidden: Boolean? = null,
    var slot: Int? = null,
    var ability: AbilityModel? = AbilityModel()
): Parcelable

@Parcelize
data class AbilityModel(
    var name: String? = null,
    var url: String? = null
): Parcelable

@Parcelize
data class StatsModel(
    var baseStat: Int? = null,
    var effort: Int? = null,
    var stat: StatModel? = StatModel()
): Parcelable

@Parcelize
data class StatModel(
    var name: String? = null,
    var url: String? = null
): Parcelable

@Parcelize
data class TypesModel(
    var slot: Int? = null,
    var type: TypeModel? = TypeModel()
): Parcelable

@Parcelize
data class TypeModel(
    var name: String? = null,
    var url: String? = null
): Parcelable

@Parcelize
data class SpritesModel(
    var backDefault: String? = null,
    var backFemale: String? = null,
    var backShiny: String? = null,
    var backShinyFemale: String? = null,
    var frontDefault: String? = null,
    var frontFemale: String? = null,
    var frontShiny: String? = null,
    var frontShinyFemale: String? = null
): Parcelable