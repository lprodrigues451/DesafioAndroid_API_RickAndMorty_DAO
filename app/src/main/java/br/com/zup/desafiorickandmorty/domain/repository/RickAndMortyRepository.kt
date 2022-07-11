package br.com.zup.desafiorickandmorty.domain.repository

import br.com.zup.desafiorickandmorty.data.datasource.local.dao.RickAndMortyDAO
import br.com.zup.desafiorickandmorty.data.datasource.remote.RetrofitService
import br.com.zup.desafiorickandmorty.data.model.RickAndMortyResponse
import br.com.zup.desafiorickandmorty.data.model.Result

class RickAndMortyRepository(private val rickAndMortyDAO: RickAndMortyDAO) {

    suspend fun getAllRickAndMorty(): List<Result> = rickAndMortyDAO.getAllRickAndMorty()

    suspend fun insertAllRickAndMortyDB(listResult: List<Result>) {
        rickAndMortyDAO.insertAllRickAndMorty(listResult)
    }

    suspend fun getAllPersonageFavorited(): List<Result> = rickAndMortyDAO.getAllPersonageFavorited()

    suspend fun updatePersonageFavorited(result: Result){
        rickAndMortyDAO.updatePersonageFavorite(result)
    }

    suspend fun getAllNetwork(): RickAndMortyResponse {
        return RetrofitService.apiService.getRickAndMorty()
    }
}