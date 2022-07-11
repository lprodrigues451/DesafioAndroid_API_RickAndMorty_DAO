package br.com.zup.desafiorickandmorty.ui.detail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.domain.model.SingleLiveEvent
import br.com.zup.desafiorickandmorty.domain.usecase.RickAndMortyUseCase
import br.com.zup.desafiorickandmorty.ui.NAO_FOI_POSSIVEL_ATUALIZAR
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalheViewModel(application: Application) : AndroidViewModel(application){
    private val rickAndMortyUseCase = RickAndMortyUseCase(application)
    val personageFavoriteState = SingleLiveEvent<ViewState<Result>>()

    fun updatePersonageFavorited(result: Result) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    rickAndMortyUseCase.updatePersonageFavorite(result)
                }
                personageFavoriteState.value = response
            } catch (ex: Exception) {
                personageFavoriteState.value =
                    ViewState.Error(Throwable(NAO_FOI_POSSIVEL_ATUALIZAR))
            }
        }
    }

}