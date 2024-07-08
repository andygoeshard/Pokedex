package cache

import app.cash.sqldelight.ColumnAdapter
import data.models.Pokedex
import data.models.PokedexResults
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.andy.pokedex.cache.PokedexDatabase
import org.andy.pokedex.cache.Pokemon

val pokedexResultsAdapter = object : ColumnAdapter<List<PokedexResults>, String> {
    override fun decode(databaseValue: String): List<PokedexResults> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: List<PokedexResults>): String {
        return Json.encodeToString(value)
    }
}


class Database(driverFactory: DatabaseDriverFactory) {
    private val database = PokedexDatabase(
        driver = driverFactory.createDriver(),
        PokemonAdapter = Pokemon.Adapter(resultsAdapter = pokedexResultsAdapter)
    )

    private val dbQuery = database.pokedexDatabaseQueries

    fun getAll(): Pokedex? {
        return dbQuery.getAll(::mapPokedexSelecting).executeAsOneOrNull()
    }

    fun update(pokedex: Pokedex) {
        dbQuery.insertPokedex(
            pokedex.count.toLong(),
            pokedex.next,
            pokedex.previous,
            pokedex.results
        )
    }

    fun clearDatabase() {
        dbQuery.deleteAll()
    }

    private fun mapPokedexSelecting(
        count: Long,
        next: String,
        previous: String,
        results: List<PokedexResults>
    ): Pokedex {
        return Pokedex(
            count = count.toInt(),
            next = next,
            previous = previous,
            results = results
        )
    }


}