package cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.andy.pokedex.cache.PokedexDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = PokedexDatabase.Schema,
            context = context,
            name = "PokedexDatabase.db"
        )
    }
}