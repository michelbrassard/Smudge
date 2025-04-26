package edu.rit.mb6149.smudge.canvas

import androidx.compose.runtime.Composable
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.rit.mb6149.smudge.Storage
import edu.rit.mb6149.smudge.model.Artwork
import edu.rit.mb6149.smudge.model.BrushType
import edu.rit.mb6149.smudge.canvas.toolbars.BrushToolbar
import edu.rit.mb6149.smudge.canvas.toolbars.ColorPicker
import edu.rit.mb6149.smudge.canvas.toolbars.DownloadOptions
import edu.rit.mb6149.smudge.canvas.toolbars.EraserToolbar
import edu.rit.mb6149.smudge.canvas.toolbars.layers.Layers
import edu.rit.mb6149.smudge.canvas.toolbars.PaintRollerToolbar

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun CanvasPage(
    navController: NavHostController,
    currentArtwork: Artwork
) {
    val artwork by rememberUpdatedState(currentArtwork)
    var selectedTool by remember { mutableStateOf(Tool.BRUSH) }

    var isColorsOpen by remember { mutableStateOf(false) }
    var isLayersOpen by remember { mutableStateOf(false) }
    var isDownloadOpen by remember { mutableStateOf(false) }
    var isToolbarOpen by remember { mutableStateOf(false) }

    var currentLayerPosition by remember { mutableIntStateOf(0) }

    var color by remember { mutableIntStateOf(Color.BLACK) }
    var strokeWidth by remember { mutableFloatStateOf(100f) }
    var style by remember { mutableStateOf(Paint.Style.STROKE) }
    var brushType by remember { mutableStateOf(BrushType.PEN) }
    var blendMode by remember { mutableStateOf(BlendMode.SRC_OVER) }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CanvasTopAppBar(
                isColorsOpen = { updateIsColorsOpen ->
                    isColorsOpen = updateIsColorsOpen
                },
                isLayersOpen = { updateIsLayersOpen ->
                    isLayersOpen = updateIsLayersOpen
                },
                isDownloadOpen = { updateIsDownloadOpen ->
                    isDownloadOpen = updateIsDownloadOpen
                },
                navController
            )
        },
        bottomBar = {
            CanvasBottomAppBar(
                isToolbarOpen = { updateIsSizeOpen ->
                    isToolbarOpen = updateIsSizeOpen
                },
                updateBrushStyle = { updateBrushStyle ->
                    style = updateBrushStyle
                },
                updateSelectedTool = { updateSelectedTool ->
                    selectedTool = updateSelectedTool
                },
                selectedTool = selectedTool,
                updateBlendMode = { updateBlendMode ->
                    blendMode = updateBlendMode
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            DrawingCanvas(
                color,
                strokeWidth,
                style,
                brushType,
                artwork,
                currentLayerPosition,
                blendMode
            )
        }
        DisposableEffect(Unit) {
            onDispose {
                Storage.save(artwork)
            }
        }
    }

    when {
        isColorsOpen -> {
            ColorPicker(
                pickedColor = { updateColor ->
                    color = updateColor
                },
                initialColor = color,
                updateIsColorsOpen = { updateIsColorsOpen ->
                    isColorsOpen = updateIsColorsOpen
                },
            )
        }
        isLayersOpen -> {
            Layers(
                updateIsLayersOpen = { updatedLayersState ->
                    isLayersOpen = updatedLayersState
                },
                updateLayerPosition = { updatedLayerPosition ->
                    currentLayerPosition = updatedLayerPosition
                },
                currentLayerPosition = currentLayerPosition,
                currentArtwork = artwork
            )
        }
        isDownloadOpen -> {
            DownloadOptions(
                updateIsDownloadOpen = { updatedDownloadState ->
                    isDownloadOpen = updatedDownloadState
                }
            )
        }
        isToolbarOpen -> {
            when (selectedTool) {
                Tool.BRUSH -> {
                    BrushToolbar(
                        updateIsToolbarOpen = { updatedToolbarState ->
                            isToolbarOpen = updatedToolbarState
                        },
                        updateStrokeWidth = { updatedStrokeWidth ->
                            strokeWidth = updatedStrokeWidth
                        },
                        updateBrushType = { updatedBrushType ->
                            brushType = updatedBrushType
                        },
                        currentStrokeWidth = strokeWidth,
                        currentBrush = brushType,
                    )
                }
                Tool.ERASER -> {
                    EraserToolbar(
                        updateIsToolbarOpen = { updatedToolbarState ->
                            isToolbarOpen = updatedToolbarState
                        },
                        updateStrokeWidth = { updatedStrokeWidth ->
                            strokeWidth = updatedStrokeWidth
                        },
                        updateBrushType = { updatedBrushType ->
                            brushType = updatedBrushType
                        },
                        currentStrokeWidth = strokeWidth,
                        currentBrush = brushType,
                    )
                }
                Tool.PAINT_ROLLER -> {
                    PaintRollerToolbar(
                        updateIsToolbarOpen = { updatedToolbarState ->
                            isToolbarOpen = updatedToolbarState
                        },
                        updateBrushType = { updatedBrushType ->
                            brushType = updatedBrushType
                        },
                        currentStrokeWidth = strokeWidth,
                        currentBrush = brushType,
                    )
                }
            }
        }
    }
}
