package br.com.zup.desafiorickandmorty.ui.favorite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.domain.model.SingleLiveEvent
import br.com.zup.desafiorickandmorty.domain.usecase.RickAndMortyUseCase
import br.com.zup.desafiorickandmorty.ui.NAO_FOI_POSSIVEL_CARREGAR_LISTA
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val rickAndMortyUseCase = RickAndMortyUseCase(application)
    val personageListFavoriteState = SingleLiveEvent<ViewState<List<Result>>>()

    fun getAllPersonageFavorited() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    rickAndMortyUseCase.getAllPersonageFavorited()
                }
                personageListFavoriteState.value = response
            } catch (ex: Exception) {
                personageListFavoriteState.value =
                    ViewState.Error(Throwable(NAO_FOI_POSSIVEL_CARREGAR_LISTA))
            }
        }
    }
}