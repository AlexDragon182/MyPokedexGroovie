package com.example.my_pokedex.features.PokemonDetails.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.my_pokedex.R
import com.example.my_pokedex.databinding.FragmentPokedexBinding
import com.example.my_pokedex.databinding.FragmentPokemonDetailsBinding
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsModel
import com.example.my_pokedex.features.pokedex.domain.PokemonModel
import com.example.my_pokedex.features.pokedex.presentation.PokedexAdapter
import com.example.my_pokedex.utils.Error
import com.example.my_pokedex.utils.Loading
import com.example.my_pokedex.utils.Success
import com.example.my_pokedex.utils.Type
import com.example.my_pokedex.utils.TypeColor
import com.example.my_pokedex.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    private var isLoading: Boolean = true

    private val viewModel: PokemonDetailsViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObservers()
        binding.pokemonName.text = viewModel.pokemonName
    }

    private fun registerObservers() {
        lifecycleScope.launch {
            viewModel.response.collect {
                handleViewState(it)
            }
        }
    }

    private fun handleViewState(viewState: ViewState<Any>) {
        when (viewState) {
            is Success -> {
                isLoading = false
                binding.loading.visibility = View.GONE
                if (viewState.data is PokemonDetailsModel) {
                    val response = viewState.data
                    showPokemonTypes()
                    viewModel.pokemonDetails.sprites?.frontDefault?.let {
                        setSpritesWithUrl()
                    }
                }
            }

            is Error -> {
                isLoading = false
                binding.loading.visibility = View.GONE
            }

            is Loading -> {
                isLoading = true
                binding.loading.visibility = View.VISIBLE
            }
        }
    }

    private fun showPokemonTypes() {
        val typesAmount = viewModel.pokemonDetails.types.size
        binding.type1.visibility = View.VISIBLE
        var type1text:String?  = viewModel.pokemonDetails.types.get(0).type?.name
        var type2text:String? = null
        var typeColor1: Int = when (type1text) {
            Type.BUG -> R.string.bug_color
            Type.DARK -> R.string.dark_color
            Type.DRAGON -> R.string.dragon_color
            Type.ELECTRIC -> R.string.electric_color
            Type.FAIRY -> R.string.fairy_color
            Type.FIGHTING -> R.string.fighting_color
            Type.FIRE -> R.string.fire_color
            Type.FLYING -> R.string.flying_color
            Type.GHOST -> R.string.ghost_color
            Type.GRASS -> R.string.grass_color
            Type.GROUND -> R.string.ground_color
            Type.ICE -> R.string.ice_color
            Type.NORMAL -> R.string.normal_color
            Type.POISON -> R.string.poison_color
            Type.PSYCHIC -> R.string.psychic_color
            Type.ROCK -> R.string.rock_color
            Type.STEEL -> R.string.steel_color
            Type.WATER -> R.string.water_color
            else -> {
                R.string.water_color
            }
        }
        binding.type1Text.text = type1text
        binding.type1.setCardBackgroundColor(Color.parseColor(getString(typeColor1)))
        //when()
        if (typesAmount > 1){
            type2text = viewModel.pokemonDetails.types.get(1).type?.name
            binding.type2.visibility = View.VISIBLE
            var typeColor2: Int = when (type2text) {
                Type.BUG -> R.string.bug_color
                Type.DARK -> R.string.dark_color
                Type.DRAGON -> R.string.dragon_color
                Type.ELECTRIC -> R.string.electric_color
                Type.FAIRY -> R.string.fairy_color
                Type.FIGHTING -> R.string.fighting_color
                Type.FIRE -> R.string.fire_color
                Type.FLYING -> R.string.flying_color
                Type.GHOST -> R.string.ghost_color
                Type.GRASS -> R.string.grass_color
                Type.GROUND -> R.string.ground_color
                Type.ICE -> R.string.ice_color
                Type.NORMAL -> R.string.normal_color
                Type.POISON -> R.string.poison_color
                Type.PSYCHIC -> R.string.psychic_color
                Type.ROCK -> R.string.rock_color
                Type.STEEL -> R.string.steel_color
                Type.WATER -> R.string.water_color
                else -> {
                    R.string.water_color
                }
            }
            binding.type2Text.text = type2text
            binding.type2.setCardBackgroundColor(Color.parseColor(getString(typeColor2)))
        }

    }

    private fun setSpritesWithUrl() {
        val imageUrlFront = viewModel.pokemonDetails.sprites?.frontDefault
        val imageUrlBack = viewModel.pokemonDetails.sprites?.backDefault
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        glide.load(imageUrlFront).transition(
            DrawableTransitionOptions
                .withCrossFade(factory)).into(binding.pokemonSpriteFront)
        glide.load(imageUrlBack).transition(
            DrawableTransitionOptions
                .withCrossFade(factory)).into(binding.pokemonSpriteBack)
    }

    private fun setSpriteBitmap(pokemonSpriteString: String): Bitmap {
        val imageByteArray: ByteArray = Base64.decode(pokemonSpriteString, Base64.DEFAULT)
        val imageBytes = Base64.decode(pokemonSpriteString, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }

}