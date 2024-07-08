package ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.viewmodel.PokedexViewModel

@Composable
fun PokedexScreen(viewModel: PokedexViewModel){

    val homeScreenState by viewModel.homeViewState.collectAsState()

    when (homeScreenState) {
        is PokedexViewModel.HomeScreenState.Loading -> {
            PiProgressIndicator()
        }
        is PokedexViewModel.HomeScreenState.Success -> {
            val pokemones = (homeScreenState as PokedexViewModel.HomeScreenState.Success).responseData.results
            PokemonCard(pokemones)
        }
        is PokedexViewModel.HomeScreenState.Error -> {
            //show Error
        }
    }
}