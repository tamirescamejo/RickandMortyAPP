package br.com.zup.rickandmorty.ui.characterfavorite.viewmodel

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

class CharacterFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    val characterListFavoriteState = SingleLiveEvent<ViewState<List<CharacterResult>>>()
    val characterDisfavorState = SingleLiveEvent<ViewState<CharacterResult>>()

    fun disfavorCharacter(character: CharacterResult) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.updateCharactersFavorite(character)
                }
                characterDisfavorState.value = response
            } catch (ex: Exception) {
                characterDisfavorState.value =
                    ViewState.Error(Throwable("Não foi desfavoritar o personagem!"))
            }
        }
    }

    fun getAllCharactersFavorited() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.getAllCharactersFavorited()
                }
                characterListFavoriteState.value = response
            } catch (ex: Exception) {
                characterListFavoriteState.value =
                    ViewState.Error(Throwable("Não foi carregar a lista de personagens favoritos!"))
            }
        }
    }
}