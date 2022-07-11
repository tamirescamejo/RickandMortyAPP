package br.com.zup.rickandmorty.ui.characterlist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

class CharacterAdapter(
    private var characterList: MutableList<CharacterResult>,
    private val clickCharacter: (characterResult: CharacterResult) -> Unit,
    private val clickFavorite: (characterResult: CharacterResult) -> Unit
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

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
    }

    override fun getItemCount() = characterList.size

    fun updateCharacterList(newList: MutableList<CharacterResult>) {
        characterList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun showCharacterInfo(characterResult: CharacterResult) {
            binding.tvCharacterName.text = characterResult.name
            Picasso.get().load(characterResult.image)
                .into(binding.ivCharacterImage)
            binding.tvCharacterName.text = characterResult.name
        }
    }
}