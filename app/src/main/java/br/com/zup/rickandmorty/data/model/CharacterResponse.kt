package br.com.zup.rickandmorty.data.model

import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: IDNA.Info,
    @SerializedName("results")
    val characterResults: List<CharacterResult>
)

