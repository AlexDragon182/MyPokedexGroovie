package com.example.my_pokedex.di

import com.example.my_pokedex.features.PokemonDetails.data.PokemonDetailsRepositoryImpl
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsRepository
import com.example.my_pokedex.features.pokedex.data.PokedexRepositoryImpl
import com.example.my_pokedex.features.pokedex.domain.PokedexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class InterfaceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPokedexRepository(repositoryImpl: PokedexRepositoryImpl): PokedexRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPokemonDetailsRepository(repositoryImpl: PokemonDetailsRepositoryImpl): PokemonDetailsRepository
}