package cache

import app.cash.sqldelight.db.SqlDriver
import org.andy.pokedex.cache.PokedexDatabase
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = PokedexDatabase.Schema,
            name = "PokedexDatabase.db"
        )
    }
}