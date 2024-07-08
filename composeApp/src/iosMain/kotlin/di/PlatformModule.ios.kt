package di

import cache.DatabaseDriverFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single{ DatabaseDriverFactory()}
}
