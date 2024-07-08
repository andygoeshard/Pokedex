import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.screen.PokedexScreen
import ui.viewmodel.PokedexViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                val viewModel = koinViewModel<PokedexViewModel>()
                PokedexScreen(viewModel)
            }
        }
    }
}
