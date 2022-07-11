package br.com.zup.rickandmorty.domain.usecase

import android.app.Application
import br.com.zup.rickandmorty.data.datasource.local.dao.CharacterDatabase
import br.com.zup.rickandmorty.data.model.CharacterResult
import br.com.zup.rickandmorty.domain.repository.CharacterRepository
import br.com.zup.rickandmorty.ui.viewstate.ViewState

class CharacterUseCase(application: Application) {
    private val characterDAO = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDAO)

    fun getAllCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharacters()
            ViewState.Success(characters)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível carregar a lista de personagens do Banco de Dados!"))
        }
    }

    fun getAllCharactersFavorited(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharactersFavorited()
            ViewState.Success(characters)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível carregar a lista de personagens favoritos!"))
        }
    }

    fun updateCharactersFavorite(character: CharacterResult): ViewState<CharacterResult> {
        return try {
            characterRepository.updateCharacterFavorited(character)
            ViewState.Success(character)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível atualizar o personagem!"))
        }
    }

    suspend fun getAllCharactersNetwork(): ViewState<List<CharacterResult>> {
        return try {
            val response = characterRepository.geAllCharactersNetwork()
            characterRepository.insertAllCharactersDB(response.characterResults)
            ViewState.Success(response.characterResults)
        } catch (ex: Exception) {
            getAllCharacters()
        }
    }
}