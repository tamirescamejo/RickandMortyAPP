package br.com.zup.rickandmorty.ui.characterdetail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import br.com.zup.rickandmorty.CHARACTER_KEY
import br.com.zup.rickandmorty.R
import br.com.zup.rickandmorty.URL_BASE_IMAGE
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.databinding.FragmentCharacterDetailBinding
import br.com.zup.rickandmorty.ui.home.view.HomeActivity
import com.squareup.picasso.Picasso

class CharacterDetailFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPassedData()
    }

    private fun getPassedData() {
        val character = arguments?.getParcelable<CharacterResult>(CHARACTER_KEY)

        character?.let {
            Picasso.get().load(URL_BASE_IMAGE)
                .into(binding.ivCharacter)

            binding.tvCharacterNameEdit.text = it.name

            binding.tvCharacterStatusEdit.text = it.status

            binding.tvCharacterSpeciesEdit.text = it.species

            binding.tvCharacterGenderEdit.text = it.gender

            binding.ivFavoriteDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    if (it.isFavorite)
                        R.drawable.ic_star
                    else
                        R.drawable.ic_star_outline
                )
            )

            (activity as HomeActivity).supportActionBar?.title = it.name
        }
    }
}