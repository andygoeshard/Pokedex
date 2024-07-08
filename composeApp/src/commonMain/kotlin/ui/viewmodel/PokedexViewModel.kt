package ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cache.Database
import data.models.Pokedex
import data.network.ApiStatus
import di.networkModule
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.compose.getKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PokedexViewModel(
    private val httpClient: HttpClient,
    private val database: Database
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    private val _homeViewState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val homeViewState = _homeViewState.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init{
        viewModelScope.launch {
            try{
                getClient()
            }
            catch (e: Exception){
                loadPokedex()
            }
        }
    }

    suspend fun getClient(urls: String = "https://pokeapi.co/api/v2/pokemon?limit=200&offset=0") {
        val response = httpClient.get(urls).body<Pokedex>()
        _homeState.update{it.copy(isLoading = false, errorMessage = "", responseData = response)}
        _homeViewState.value = _homeState.value.toUiState()
        if(_homeState.value.responseData != null){
            savePokedex(response)
        }
    }

    suspend fun savePokedex(pokedex: Pokedex){
        database.update(pokedex)
    }

    suspend fun loadPokedex(){
        val response = database.getAll()
        _homeState.update{it.copy(isLoading = false, errorMessage = "", responseData = response)}
        _homeViewState.value = _homeState.value.toUiState()
    }


    sealed class HomeScreenState {
        data object Loading: HomeScreenState()
        data class Error(val errorMessage: String):HomeScreenState()
        data class Success(val responseData: Pokedex):HomeScreenState()
    }

    private data class HomeState(
        val isLoading:Boolean = true,
        val errorMessage: String?=null,
        val responseData: Pokedex?=null
    ) {
        fun toUiState(): HomeScreenState {
            return if (isLoading) {
                HomeScreenState.Loading
            } else if(errorMessage?.isNotEmpty()==true) {
                HomeScreenState.Error(errorMessage)
            } else {
                HomeScreenState.Success(responseData!!)
            }
        }
    }
}