import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinApplication
import ui.screen.PokedexScreen

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                PokedexScreen()
            }
        }
    }

}
