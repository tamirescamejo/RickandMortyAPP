package br.com.zup.rickandmorty.ui.characterlist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.rickandmorty.CHARACTER_KEY
import br.com.zup.rickandmorty.R
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.databinding.FragmentCharacterListBinding
import br.com.zup.rickandmorty.ui.characterlist.viewmodel.CharacterViewModel
import br.com.zup.rickandmorty.ui.home.view.HomeActivity
import br.com.zup.rickandmorty.ui.viewstate.ViewState

class CharacterListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterListBinding

    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter(arrayListOf(), this::goToCharacterDetail, this::favoritedCharacter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setUpRvCharacterList()
        viewModel.getAllCharacters()
        initObserver()
    }

    private fun initObserver() {
        viewModel.characterListState.observe(this.viewLifecycleOwner) {

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

        viewModel.characterFavoritedState.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Success -> {
                    Toast.makeText(
                        context,
                        "Personagem ${it.data.name} foi favoritado com sucesso!",
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

        viewModel.loading.observe(this.viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.pbLoading.isVisible = it.loading == true
                }
                else -> {}
            }
        }
}

    private fun setUpRvCharacterList() {
        binding.rvCharacterList.adapter = adapter
        binding.rvCharacterList.layoutManager = GridLayoutManager(context, 2)
    }

    private fun goToCharacterDetail(character: CharacterResult) {
        val bundle = bundleOf(CHARACTER_KEY to character)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_characterListFragment_to_characterDetailFragment, bundle
        )
    }

    private fun favoritedCharacter(character: CharacterResult) {
        viewModel.updateCharacterFavorited(character)
    }
}