package br.com.zup.rickandmorty.ui.characterfavorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.rickandmorty.R
import br.com.zup.rickandmorty.URL_BASE_IMAGE
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

class CharacterFavoriteAdapter(
    private var characterList: MutableList<CharacterResult>,
    private val clickCharacter: (characterResult: CharacterResult) -> Unit,
    private val clickDisfavor: (characterResult: CharacterResult) -> Unit
) :
    RecyclerView.Adapter<CharacterFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.showCharacterInfo(character)
        holder.binding.ivCharacterImage.setOnClickListener {
            clickCharacter(character)
        }
        holder.binding.ivFavorite.setOnClickListener {
            character.isFavorite = !character.isFavorite
            clickDisfavor(character)
            characterList.remove(character)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = characterList.size

    fun updateCharacterList(newList: MutableList<CharacterResult>) {
        characterList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun showCharacterInfo(characterResult: CharacterResult) {
            binding.tvCharacterName.text = characterResult.name
            Picasso.get().load(URL_BASE_IMAGE + characterResult)
                .into(binding.ivCharacterImage)

            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    if (characterResult.isFavorite)
                        R.drawable.ic_star
                    else
                        R.drawable.ic_star_outline
                )
            )
        }
    }
}