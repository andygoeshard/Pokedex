package ui.viewmodel

import androidx.lifecycle.ViewModel
import data.models.Pokedex
import data.network.ApiStatus
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

class PokedexViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    private val _homeViewState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val homeViewState = _homeViewState.asStateFlow()
    val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    suspend fun getClient(url: String = "https://pokeapi.co/api/v2/pokemon?limit=200&offset=0") {
        val response = httpClient.get(url).body<Pokedex>()
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