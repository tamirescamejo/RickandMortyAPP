package br.com.zup.rickandmorty.domain.repository

import br.com.zup.rickandmorty.data.datasource.local.dao.CharacterDAO
import br.com.zup.rickandmorty.data.datasource.remote.RetrofitService
import br.com.zup.rickandmorty.data.model.CharacterResponse
import br.com.zup.rickandmorty.data.model.CharacterResult

class CharacterRepository(private val characterDAO: CharacterDAO) {

    fun getAllCharacters(): List<CharacterResult> = characterDAO.getAllCharacters()

    fun insertAllCharactersDB(characterList: List<CharacterResult>) {
        characterDAO.insertAllCharacters(characterList)
    }

    fun getAllCharactersFavorited(): List<CharacterResult> =
        characterDAO.getAllCharactersFavorited()

    fun updateCharacterFavorited(character: CharacterResult) {
        characterDAO.updateCharacterFavorite(character)
    }

    suspend fun geAllCharactersNetwork(): CharacterResponse {
        return RetrofitService.apiService.getAllCharactersNetwork()
    }
}