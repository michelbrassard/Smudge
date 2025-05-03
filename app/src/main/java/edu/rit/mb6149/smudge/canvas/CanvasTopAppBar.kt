package edu.rit.mb6149.smudge.canvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import edu.rit.mb6149.smudge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasTopAppBar(
    isColorsOpen: (Boolean) -> Unit,
    isLayersOpen: (Boolean) -> Unit,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            IconButton(onClick = {
                navController.navigate("gallery")
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Description")
            }
        },
        actions = {
            IconButton(onClick = {
                isLayersOpen(true)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.layers_2),
                    contentDescription = "Layers"
                )
            }
            IconButton(onClick = {
                isColorsOpen(true)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.palette),
                    contentDescription = "Palette"
                )
            }
        }
    )
}