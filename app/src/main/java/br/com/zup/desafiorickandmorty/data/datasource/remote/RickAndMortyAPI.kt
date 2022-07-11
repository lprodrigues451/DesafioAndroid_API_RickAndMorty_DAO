package br.com.zup.desafiorickandmorty.data.datasource.remote

import br.com.zup.desafiorickandmorty.data.model.RickAndMortyResponse
import retrofit2.http.GET

interface RickAndMortyAPI {
    @GET("character")
    suspend fun getRickAndMorty(): RickAndMortyResponse
}