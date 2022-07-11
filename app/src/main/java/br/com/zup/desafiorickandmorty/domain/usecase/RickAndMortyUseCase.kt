package br.com.zup.desafiorickandmorty.domain.usecase

import android.app.Application
import br.com.zup.desafiorickandmorty.data.datasource.local.RickAndMortyDatabase
import br.com.zup.desafiorickandmorty.data.datasource.local.dao.RickAndMortyDAO
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.domain.repository.RickAndMortyRepository
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState

class RickAndMortyUseCase(application: Application) {
    private val rickAndMortyDAO = RickAndMortyDatabase.getDatabase(application).rickAndMorty()
    private val rickAndMortyRepository = RickAndMortyRepository(rickAndMortyDAO)

    suspend fun getAllRickAndMortyNetwork(): ViewState<List<Result>> {
        return try {
            val rickAndMorty = rickAndMortyRepository.getAllNetwork()
            rickAndMortyRepository.insertAllRickAndMortyDB(rickAndMorty.results)
            getAllRickAndMorty()
        } catch (ex: Exception) {
            getAllRickAndMorty()
        }
    }
    private suspend fun getAllRickAndMorty(): ViewState<List<Result>> {
        return try {
            val rickAndMorty = rickAndMortyRepository.getAllRickAndMorty()
            ViewState.Success(rickAndMorty)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível carregar a lista  de personagem no Banco de Dados!"))
        }
    }

    suspend fun getAllPersonageFavorited(): ViewState<List<Result>>{
        return try {
            val personage = rickAndMortyRepository.getAllPersonageFavorited()
            ViewState.Success(personage)
        }catch (ex: Exception){
            ViewState.Error(Exception("Não foi possível carregar a lista de personagem favoritos!"))
        }
    }

    suspend fun updatePersonageFavorite(result: Result): ViewState<Result>{
        return try {
            rickAndMortyRepository.updatePersonageFavorited(result)
            ViewState.Success(result)
        }catch (ex: Exception){
            ViewState.Error(Exception("Não foi possível atualizar do personagem!"))
        }
    }
}