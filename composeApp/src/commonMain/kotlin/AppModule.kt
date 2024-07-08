import di.databaseModule
import di.networkModule
import di.provideViewModelModule

fun appModule() = listOf(
    provideViewModelModule,
    networkModule,
    databaseModule
)