package ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.viewmodel.PokedexViewModel
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.getKoin

@Composable
fun PokedexScreen(){
    val viewModel: PokedexViewModel = getKoin().get()
    val homeScreenState by viewModel.homeViewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getClient()
    }
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