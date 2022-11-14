package com.camacho.cucsur.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.rememberAsyncImagePainter
import com.camacho.cucsur.R
import com.camacho.cucsur.models.CharacterDetail
import com.camacho.cucsur.models.Location
import com.camacho.cucsur.models.Origin
import com.camacho.cucsur.theme.CucSurTheme
import com.camacho.cucsur.viewmodels.CharacterViewModel

@Composable
fun CharactersUI() {
    val viewModel = CharacterViewModel()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    itemsIndexed(viewModel.charactersList) { index, item ->
                        ItemCharacter(item)

                        if (index == viewModel.charactersList.lastIndex) {
                            viewModel.getAllCharacters()
                        }
                    }
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemCharacter(detail: CharacterDetail) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowDialog(content = {
            FullCharacter(detail = detail)
        }, onDismissRequest = {
            showDialog.value = false
        })
    }

    Card(shape = RoundedCornerShape(6.dp), onClick = {
        showDialog.value = true
    }) {
        Column {
            val image = rememberAsyncImagePainter(
                model = detail.image,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                filterQuality = FilterQuality.High
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(

                    painter = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment.Rectangle
                        )
                )
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0x80D3CDCD)) {

                }
                Image(
                    painter = image,
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = detail.name,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }

}

@Composable
private fun FullCharacter(detail: CharacterDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Surface(
            shape = CircleShape,
            modifier = Modifier
                .size(90.dp)
                .align(CenterHorizontally),
            border = BorderStroke(1.dp, color = Color.Black),

            ) {
            val image = rememberAsyncImagePainter(
                model = detail.image,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                filterQuality = FilterQuality.High
            )

            Image(
                painter = painterResource(id = R.drawable.cumlee),
//                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }


        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Nombre: ${detail.name}")

            Row(modifier = Modifier.align(CenterEnd)) {
                Text(text = detail.status, modifier = Modifier)
                Spacer(modifier = Modifier.size(5.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color = if (detail.status == "Alive") Color.Green else Color.Red, shape = CircleShape)
                        .border(1.dp, color = Color.LightGray, CircleShape)
                        .align(CenterVertically)
                )
            }
        }

        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Text(text = detail.location.name)
        }

        Text(text = detail.gender)


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ShowDialog(content: @Composable (ColumnScope) -> Unit, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn,
            usePlatformDefaultWidth = true
        )
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Column {
                content.invoke(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemCharacterPreview() {
    CucSurTheme {
        val character = CharacterDetail(
            name = "Panduro",
            species = "Chaka",
            gender = "Machin",
            status = "Vivito y coleando",
            created = "kjh",
            episode = emptyList(),
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = Location(name = "El tecolote", url = ""),
            origin = Origin(name = "Autlan", url = ""),
            type = "",
            url = ""
        )
        Column(modifier = Modifier.padding(5.dp)) {
            FullCharacter(detail = character)
        }
    }
}