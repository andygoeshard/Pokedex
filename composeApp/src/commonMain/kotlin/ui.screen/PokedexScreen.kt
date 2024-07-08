package ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.viewmodel.PokedexViewModel
import androidx.compose.runtime.LaunchedEffect
import cache.Database
import org.koin.compose.getKoin

@Composable
fun PokedexScreen(viewModel: PokedexViewModel){

    val homeScreenState by viewModel.homeViewState.collectAsState()

    when (homeScreenState) {
        is PokedexViewModel.HomeScreenState.Loading -> {
            PiProgressIndicator()
        }
        is PokedexViewModel.HomeScreenState.Success -> {
            val products = (homeScreenState as PokedexViewModel.HomeScreenState.Success).responseData.results
            PokemonCard(products)
        }
        is PokedexViewModel.HomeScreenState.Error -> {
            //show Error
        }
    }
}