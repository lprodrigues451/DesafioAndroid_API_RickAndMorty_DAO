package br.com.zup.desafiorickandmorty.ui.main.viewmodel

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

class MainViewModel(application:Application): AndroidViewModel(application) {
    private val rickAndMortyUseCase = RickAndMortyUseCase(application)
    val rickAndMortyListState = SingleLiveEvent<ViewState<List<Result>>>()

    fun getAllRickAndMorty() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    rickAndMortyUseCase.getAllRickAndMortyNetwork()
                }
                rickAndMortyListState.value = response
            } catch (ex: Exception) {
                rickAndMortyListState.value =
                    ViewState.Error(Throwable(NAO_FOI_POSSIVEL_CARREGAR_LISTA))
            }
        }
    }
}