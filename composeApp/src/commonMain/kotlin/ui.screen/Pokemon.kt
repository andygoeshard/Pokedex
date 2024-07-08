package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import domain.ImageBuilder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.models.PokedexResults
import domain.StringManipulator.capitalizeFirstLetter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun PokemonCard(list: List<PokedexResults>) {

    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = Color.Black,
                backgroundColor = Color.White,
                title = {
                    Text(
                        "Pokedex",
                        maxLines = 1,
                    )
                }
            )
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(list.size) { index ->
                    val data = list[index]
                    Pokemon(data.name.capitalizeFirstLetter(), ImageBuilder.buildPokemonImageByUrl(data.url))

                }
            }
        )
    }
}


@Composable
fun Pokemon(name: String, url: String) {

    var cardClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { cardClicked = true },
        shape = MaterialTheme.shapes.small,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {

        Column(modifier = Modifier.height(250.dp).padding(10.dp)) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    text = name,
                    maxLines = 3,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(20.dp))
                KamelImage(
                    resource = asyncPainterResource(data = url),
                    contentDescription = null,
                    modifier = Modifier.height(150.dp).fillMaxWidth().clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,)

            }
        }
    }
    if(cardClicked) PokemonDialog(name, url, onDismiss = { cardClicked = false })
}

@Composable
fun PokemonDialog(name: String, url: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable { onDismiss() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.44f)
                    .align(Alignment.Center)
                    .background(Color.Black, shape = MaterialTheme.shapes.medium)
                    .clickable(false) {}
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

                    Spacer(modifier = Modifier.height(20.dp))

                    KamelImage(
                        resource = asyncPainterResource(data = url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )

                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    ) {

                        Text(text = "Cerrar",
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}