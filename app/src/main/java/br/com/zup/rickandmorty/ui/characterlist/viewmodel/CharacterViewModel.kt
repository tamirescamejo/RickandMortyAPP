package br.com.zup.rickandmorty.ui.characterlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.domain.model.SingleLiveEvent
import br.com.zup.rickandmorty.domain.usecase.CharacterUseCase
import br.com.zup.rickandmorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(application: Application) : AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    val characterListState = SingleLiveEvent<ViewState<List<CharacterResult>>>()
    val characterFavoritedState = SingleLiveEvent<ViewState<CharacterResult>>()
    val loading = SingleLiveEvent<ViewState<Boolean>>()

    fun getAllCharacters() {
        loading.value = ViewState.Loading(true)
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.getAllCharactersNetwork()
                }
                characterListState.value = response
            } catch (ex: Exception) {
                characterListState.value =
                    ViewState.Error(Throwable("Não foi possível carregar a lista vinda da internet!"))
            } finally {
                loading.value = ViewState.Loading(false)
            }
        }
    }

    fun updateCharacterFavorited(characterResult: CharacterResult) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.updateCharactersFavorite(characterResult)
                }
                characterFavoritedState.value = response
            } catch (ex: Exception) {
                characterFavoritedState.value =
                    ViewState.Error(Throwable("Não foi possível atualizar o personagem!"))
            }
        }
    }
}