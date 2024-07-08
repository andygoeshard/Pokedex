package di

import cache.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        DatabaseDriverFactory(get())
    }
}