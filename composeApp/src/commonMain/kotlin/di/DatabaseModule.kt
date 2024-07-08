package di

import cache.Database
import org.koin.dsl.module

val databaseModule = module{
    single { Database(get()) }
}