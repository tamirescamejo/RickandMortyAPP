package br.com.zup.rickandmorty.data.datasource.local.dao

import androidx.room.*
import br.com.zup.rickandmorty.data.model.CharacterResult

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): List<CharacterResult>

    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    fun getAllCharactersFavorited(): List<CharacterResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCharacters(listMovies: List<CharacterResult>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateCharacterFavorite(character: CharacterResult)
}