package com.example.my_pokedex.features.pokedex.presentation

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pokedex.databinding.FragmentPokedexBinding
import com.example.my_pokedex.features.PokemonDetails.domain.PokemonDetailsModel
import com.example.my_pokedex.features.pokedex.domain.PokemonModel
import com.example.my_pokedex.features.pokedex.domain.PokemonResultModel
import com.example.my_pokedex.utils.Error
import com.example.my_pokedex.utils.Loading
import com.example.my_pokedex.utils.Success
import com.example.my_pokedex.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PokedexFragment : Fragment() {

    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pokedexAdapter: PokedexAdapter
    private val viewModel: PokedexViewModel by viewModels()

    private var previousLastVisibleItem = 0
    private var previousLastVisibleItemCache = previousLastVisibleItem
    private var lastVisibleItem = 0
    private var isLoading: Boolean = true
    private var currentItem = 0
    private var totalItemCount = 0
    private var currentLastItemId = "bulbasaur"
    private var previousLastItemId = ""
    private var isFirstTimeLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokedexAdapter.setOnItemClickListener { item, adapterPosition ->
            println("clicked on pokemon: " + item.name)
            viewModel.savePokemonIntoDB(item)
            findNavController().navigate(PokedexFragmentDirections.actionPokedexFragmentToPokemonDetailsFragment(item.name))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPokemon.adapter = pokedexAdapter
        registerObservers()
        addScrollListener()
        setListeners()
        if (viewModel.pokemonList.isEmpty()) {
            viewModel.getPokemon()
        }
    }

    private fun setListeners() {
        binding.searchPokemonText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s!!
                if (s.toString().isEmpty()){
                    viewModel.setQuery(s.toString())
                    viewModel.getPokemon()
                } else {
                    viewModel.setQuery(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) {
                s!!
            }
        })

        binding.searchButton.setOnClickListener{
            viewModel.getPokemonByName()
        }


    }

    private fun addScrollListener() {
        binding.rvPokemon.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.rvPokemon.layoutManager?.let {
                        currentItem = it.childCount
                        totalItemCount = it.itemCount
                        lastVisibleItem = (it as LinearLayoutManager).findFirstVisibleItemPosition()
                        if (!isLoading && (currentItem + lastVisibleItem == totalItemCount) && currentLastItemId != previousLastItemId) {
                            isLoading = true
                            previousLastVisibleItemCache = previousLastVisibleItem
                            if (viewModel.pokemonList.size > 0) {
                                previousLastVisibleItem = viewModel.pokemonList.size - 1
                                previousLastItemId =
                                    viewModel.pokemonList.get(viewModel.pokemonList.size - 1).name
                            }
                            viewModel.getPokemon()
                        }
                    }
                }
            }
        })
    }

    private fun registerObservers() {
        lifecycleScope.launch {
            viewModel.response.collect {
                handleViewState(it)
            }
        }

        viewModel.pokemonAlreadySeenList.observe(viewLifecycleOwner) {pokemonListFromDB->
            /*
            println("Pokemon in database:")
            pokemonListFromDB.forEach{
                print(it.name + ", ")
            }
            println()
             */

            println("pokemon already seen live data was called")
            if (viewModel.pokemonList.isNotEmpty()) {
                updateAlreadySeenPokemon(viewModel.pokemonList, pokemonListFromDB.toMutableList(), true)
            }
        }

    }

    private fun updateAlreadySeenPokemon(pokemonList:MutableList<PokemonResultModel>,pokemonListFromDB: MutableList<PokemonResultModel>? = null, callFromLiveData : Boolean = false) {

        var myPokemonList = mutableListOf<PokemonResultModel>()
        var pokemonWasAdded = false

        pokemonList.forEach {pokemonFromList->
            if (!pokemonListFromDB.isNullOrEmpty()) {
                pokemonListFromDB.forEach { pokemonFromDB ->
                    if (pokemonFromDB.name.equals(pokemonFromList.name)) {
                        //println("Pokemon found in DB: " + pokemonFromList.name)
                        var pokemon = pokemonFromList.copy(hasAlreadyBeenSeen = true)
                        myPokemonList.add(pokemon)
                        pokemonWasAdded = true
                    }
                }
            }
            if (!pokemonWasAdded){
                myPokemonList.add(pokemonFromList)
            } else{
                pokemonWasAdded = false
            }
        }
        //println("contents of new list: " + myPokemonList.toString())
        viewModel.pokemonList = myPokemonList
        pokedexAdapter.submitList(viewModel.pokemonList)
        if (callFromLiveData){
            binding.rvPokemon.post(Runnable { binding.rvPokemon.smoothScrollToPosition(0) })
        }
    }

    private fun handleViewState(viewState: ViewState<Any>) {
        when (viewState) {
            is Success -> {
                isLoading = false
                binding.loading.visibility = View.GONE
                binding.noResultsText.visibility = View.GONE
                updateAlreadySeenPokemon(viewModel.pokemonList,viewModel.pokemonAlreadySeenList.value?.toMutableList())

                if (viewState.data is PokemonModel) {
                    val response = viewState.data.results
                    pokedexAdapter.submitList(viewModel.pokemonList)
                    if (isFirstTimeLoading) {
                        binding.rvPokemon.scheduleLayoutAnimation()
                        isFirstTimeLoading = false
                    }
                } else if (viewState.data is PokemonDetailsModel) {
                    val response = viewState.data.name
                    pokedexAdapter.submitList(viewModel.pokemonList)
                    binding.rvPokemon.scheduleLayoutAnimation()
                }
            }

            is Error -> {
                println("Error")
                isLoading = false
                binding.loading.visibility = View.GONE
                binding.noResultsText.visibility = View.VISIBLE
            }

            is Loading -> {
                isLoading = true
                binding.loading.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

}