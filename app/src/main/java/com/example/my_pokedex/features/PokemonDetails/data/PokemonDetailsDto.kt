package com.example.my_pokedex.features.PokemonDetails.data

import com.example.my_pokedex.features.PokemonDetails.domain.AbilitiesModel
import com.example.my_pokedex.features.PokemonDetails.domain.AbilityModel
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsModel
import com.example.my_pokedex.features.PokemonDetails.domain.SpritesModel
import com.example.my_pokedex.features.PokemonDetails.domain.StatModel
import com.example.my_pokedex.features.PokemonDetails.domain.StatsModel
import com.example.my_pokedex.features.PokemonDetails.domain.TypeModel
import com.example.my_pokedex.features.PokemonDetails.domain.TypesModel
import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(

    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("base_experience") var baseExperience: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("is_default") var isDefault: Boolean? = null,
    @SerializedName("order") var order: Int? = null,
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("abilities") var abilities: List<Abilities> = listOf(),
    @SerializedName("forms") var forms: List<Forms> = listOf(),
    @SerializedName("game_indices") var gameIndices: List<GameIndices> = listOf(),
    @SerializedName("held_items") var heldItems: List<HeldItems> = listOf(),
    @SerializedName("location_area_encounters") var locationAreaEncounters: String? = null,
    @SerializedName("moves") var moves: List<Moves> = listOf(),
    @SerializedName("species") var species: Species? = Species(),
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("stats") var stats: List<Stats> = listOf(),
    @SerializedName("types") var types: List<Types> = listOf(),
    @SerializedName("past_types") var pastTypes: List<PastTypes> = listOf()
)

data class Ability(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Abilities(

    @SerializedName("is_hidden") var isHidden: Boolean? = null,
    @SerializedName("slot") var slot: Int? = null,
    @SerializedName("ability") var ability: Ability? = Ability()

)

data class Forms(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Version(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)


data class GameIndices(

    @SerializedName("game_index") var gameIndex: Int? = null,
    @SerializedName("version") var version: Version? = Version()

)

data class Item(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)


data class VersionDetails(

    @SerializedName("rarity") var rarity: Int? = null,
    @SerializedName("version") var version: Version? = Version()

)

data class HeldItems(

    @SerializedName("item") var item: Item? = Item(),
    @SerializedName("version_details") var versionDetails: List<VersionDetails> = listOf()

)


data class Move(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class VersionGroup(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class MoveLearnMethod(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class VersionGroupDetails(

    @SerializedName("level_learned_at") var levelLearnedAt: Int? = null,
    @SerializedName("version_group") var versionGroup: VersionGroup? = VersionGroup(),
    @SerializedName("move_learn_method") var moveLearnMethod: MoveLearnMethod? = MoveLearnMethod()

)

data class Moves(

    @SerializedName("move") var move: Move? = Move(),
    @SerializedName("version_group_details") var versionGroupDetails: List<VersionGroupDetails> = listOf()

)

data class Species(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Sprites(

    @SerializedName("back_default") var backDefault: String? = null,
    @SerializedName("back_female") var backFemale: String? = null,
    @SerializedName("back_shiny") var backShiny: String? = null,
    @SerializedName("back_shiny_female") var backShinyFemale: String? = null,
    @SerializedName("front_default") var frontDefault: String? = null,
    @SerializedName("front_female") var frontFemale: String? = null,
    @SerializedName("front_shiny") var frontShiny: String? = null,
    @SerializedName("front_shiny_female") var frontShinyFemale: String? = null

)

data class Stat(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Stats(

    @SerializedName("base_stat") var baseStat: Int? = null,
    @SerializedName("effort") var effort: Int? = null,
    @SerializedName("stat") var stat: Stat? = Stat()

)


data class Generation(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Type(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Types(

    @SerializedName("slot") var slot: Int? = null,
    @SerializedName("type") var type: Type? = Type()

)

data class PastTypes(

    @SerializedName("generation") var generation: Generation? = Generation(),
    @SerializedName("types") var types: List<Types> = listOf()

)

fun PokemonDetailsDto.toPokemonDetailsModel() = PokemonDetailsModel(
    id = id,
    name = name,
    height = height,
    order = order,
    weight = weight,
    sprites = sprites?.toSpritesModel(),
    abilities = abilities.map { it.toAbilitiesModel() },
    stats = stats.map { it.toStatsModel() },
    types = types.map { it.toTypesModel() }
)

fun Ability.toAbilityModel() = AbilityModel(
    name = name,
    url = url
)

fun Abilities.toAbilitiesModel() = AbilitiesModel(
    isHidden = isHidden,
    slot = slot,
    ability = ability?.toAbilityModel()
)

fun Stats.toStatsModel() = StatsModel(
    baseStat = baseStat,
    effort = effort,
    stat = stat?.toStatModel()
)

fun Stat.toStatModel() = StatModel(
    name = name,
    url = url
)

fun Types.toTypesModel() = TypesModel(
    slot = slot,
    type = type?.toTypeModel()
)

fun Type.toTypeModel() = TypeModel(
    name = name,
    url = url
)

fun Sprites.toSpritesModel() = SpritesModel(
    backDefault = backDefault,
    backFemale = backFemale,
    backShiny = backShiny,
    backShinyFemale = backShinyFemale,
    frontDefault = frontDefault,
    frontFemale = frontFemale,
    frontShiny = frontShiny,
    frontShinyFemale = frontShinyFemale
)