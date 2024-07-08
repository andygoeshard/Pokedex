package org.andy.pokedexwow

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import cache.DatabaseDriverFactory
import cache.pokedexResultsAdapter
import org.andy.pokedex.cache.PokedexDatabase
import org.andy.pokedex.cache.Pokemon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
