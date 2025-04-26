package edu.rit.mb6149.smudge.canvas

import android.graphics.BlendMode
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.res.painterResource
import edu.rit.mb6149.smudge.R

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CanvasBottomAppBar(
    isToolbarOpen: (Boolean) -> Unit,
    updateBrushStyle: (Paint.Style) -> Unit,
    updateBlendMode: (BlendMode) -> Unit,
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
                updateBlendMode(BlendMode.CLEAR)
                updateBrushStyle(Paint.Style.STROKE)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.eraser),
                    contentDescription = "Eraser"
                )
            }
            IconButton(onClick = {
                updateSelectedTool(Tool.BRUSH)
                updateBlendMode(BlendMode.SRC_OVER)
                updateBrushStyle(Paint.Style.STROKE)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.brush),
                    contentDescription = "Brush",
                )
            }
            IconButton(
                onClick = {
                    updateSelectedTool(Tool.PAINT_ROLLER)
                    updateBrushStyle(Paint.Style.FILL)
                    updateBlendMode(BlendMode.SRC_OVER)
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