package di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.viewmodel.PokedexViewModel

val provideViewModelModule = module {
    viewModelOf(::PokedexViewModel)
}