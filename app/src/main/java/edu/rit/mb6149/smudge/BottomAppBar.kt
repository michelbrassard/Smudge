package edu.rit.mb6149.smudge

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun BottomAppBar(isBrushesOpen: (Boolean) -> Unit) {

    BottomAppBar(
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.undo_2),
                    contentDescription = "Undo"
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.redo_2),
                    contentDescription = "Redo"
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.eraser),
                    contentDescription = "Eraser"
                )
            }
            IconButton(onClick = {
                isBrushesOpen(true)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.brush),
                    contentDescription = "Brush",
                    tint = Color.Black
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.paint_roller),
                    contentDescription = "Eraser"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.brush),
                    contentDescription = "Brush"
                )
            }
        }
    )
}