package com.example.my_pokedex.features.pokedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.my_pokedex.databinding.ItemPokemonBinding
import com.example.my_pokedex.features.pokedex.domain.PokemonResultModel
import javax.inject.Inject

class PokedexAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<PokemonResultModel, PokedexAdapter.PokemonViewHolder>(CategoryContentDataDiff) {

    private var onItemClickListener: ((PokemonResultModel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (PokemonResultModel, Int) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PokemonResultModel) = with(binding) {
            pokemonName.text = item.name
            if (item.hasAlreadyBeenSeen){
                checkmark.visibility = View.VISIBLE
            } else {
                checkmark.visibility = View.GONE
            }
            /*
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            glide.load(item.imageUrl).transition(
                DrawableTransitionOptions
                    .withCrossFade(factory)).into(categoryImage)
             */
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item, adapterPosition)
            }
        }
    }
}

object CategoryContentDataDiff : DiffUtil.ItemCallback<PokemonResultModel>() {
    override fun areContentsTheSame(
        oldItem: PokemonResultModel,
        newItem: PokemonResultModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: PokemonResultModel,
        newItem: PokemonResultModel
    ): Boolean {
        return oldItem.equals(newItem)
    }
}