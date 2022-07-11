package br.com.zup.rickandmorty.ui.characterfavorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.rickandmorty.CHARACTER_KEY
import br.com.zup.rickandmorty.R
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.databinding.FragmentCharacterFavoriteBinding
import br.com.zup.rickandmorty.ui.characterfavorite.viewmodel.CharacterFavoriteViewModel
import br.com.zup.rickandmorty.ui.home.view.HomeActivity
import br.com.zup.rickandmorty.ui.viewstate.ViewState

class CharacterFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentCharacterFavoriteBinding

    private val viewModel: CharacterFavoriteViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteViewModel::class.java]
    }

    private val adapter: CharacterFavoriteAdapter by lazy {
        CharacterFavoriteAdapter(arrayListOf(), this::goToCharacterDetail, this::disfavorCharacter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initObserver()
        viewModel.getAllCharactersFavorited()
        setUpRvCharacterList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initObserver() {
        viewModel.characterListFavoriteState.observe(this.viewLifecycleOwner) {

            when (it) {
                is ViewState.Success -> {
                    adapter.updateCharacterList(it.data.toMutableList())
                }
                is ViewState.Error -> {
                    Toast.makeText(
                        context,
                        "${it.throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }

        viewModel.characterDisfavorState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Toast.makeText(
                        context,
                        "Personagem ${it.data.name} foi desfavoritado!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ViewState.Error -> {
                    Toast.makeText(
                        context,
                        "${it.throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    private fun setUpRvCharacterList() {
        binding.rvCharacterFavoriteList.adapter = adapter
        binding.rvCharacterFavoriteList.layoutManager = LinearLayoutManager(context)
    }

    private fun goToCharacterDetail(movie: CharacterResult) {
        val bundle = bundleOf(CHARACTER_KEY to movie)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterListFragment_to_characterDetailFragment, bundle
        )
    }

    private fun disfavorCharacter(character: CharacterResult) {
        viewModel.disfavorCharacter(character)
    }



}