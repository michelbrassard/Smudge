package edu.rit.mb6149.smudge

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.res.painterResource
import edu.rit.mb6149.smudge.model.Tool

@Composable
fun BottomAppBar(
    isToolbarOpen: (Boolean) -> Unit,
    brushStyle: (android.graphics.Paint.Style) -> Unit,
    updateSelectedTool: (Tool) -> Unit,
    selectedTool: Tool
) {
    val updatedSelectedTool by rememberUpdatedState(selectedTool)
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
            IconButton(onClick = {
                updateSelectedTool(Tool.ERASER)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.eraser),
                    contentDescription = "Eraser"
                )
            }
            IconButton(onClick = {
                updateSelectedTool(Tool.BRUSH)
                brushStyle(android.graphics.Paint.Style.STROKE)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.brush),
                    contentDescription = "Brush",
                )
            }
            IconButton(
                onClick = {
                    updateSelectedTool(Tool.PAINT_ROLLER)
                    brushStyle(android.graphics.Paint.Style.FILL)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.paint_roller),
                    contentDescription = "Eraser"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isToolbarOpen(true)
                },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                when (updatedSelectedTool) {
                    Tool.BRUSH -> {
                        Icon(
                            painter = painterResource(id = R.drawable.brush),
                            contentDescription = "Brush"
                        )
                    }
                    Tool.ERASER -> {
                        Icon(
                            painter = painterResource(id = R.drawable.eraser),
                            contentDescription = "Eraser"
                        )
                    }
                    Tool.PAINT_ROLLER -> {
                        Icon(
                            painter = painterResource(id = R.drawable.paint_roller),
                            contentDescription = "Paint Roller"
                        )
                    }
                }
            }
        }
    )
}