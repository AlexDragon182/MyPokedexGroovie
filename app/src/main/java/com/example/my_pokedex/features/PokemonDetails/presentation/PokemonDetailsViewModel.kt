package com.example.my_pokedex.features.PokemonDetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsModel
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsRepository
import com.example.my_pokedex.features.pokedex.domain.PokemonResultModel
import com.example.my_pokedex.localstorage.PreferencesManager
import com.example.my_pokedex.utils.Error
import com.example.my_pokedex.utils.Loading
import com.example.my_pokedex.utils.Resource
import com.example.my_pokedex.utils.Success
import com.example.my_pokedex.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonDetailsRepository,
    private val preferencesManager: PreferencesManager,
    private val state: SavedStateHandle
) : ViewModel() {

    val pokemonName = state.get<String>("pokemonName") ?: ""
    var pokemonDetails = PokemonDetailsModel()
    protected val _response = Channel<ViewState<Any>>(Channel.BUFFERED)
    val response = _response.receiveAsFlow()

    init {
        getPokemonByName()
    }

    fun getPokemonByName() {
        viewModelScope.launch {
            _response.send(Loading())
            val result = repository.getPokemonByName(pokemonName)
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        pokemonDetails = it
                        _response.send(Success(data = it))
                    }
                }


                is Resource.Error -> {
                    _response.send(Error())
                }

                is Resource.Loading -> {
                    //NO-OP//
                }
            }
        }

    }

}