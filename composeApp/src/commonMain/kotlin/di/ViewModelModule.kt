package di

import org.koin.dsl.module
import ui.viewmodel.PokedexViewModel

val provideViewModelModule = module {
    single {
        PokedexViewModel()
    }
}