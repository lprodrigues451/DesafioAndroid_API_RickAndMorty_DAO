package br.com.zup.desafiorickandmorty.data.model


import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)