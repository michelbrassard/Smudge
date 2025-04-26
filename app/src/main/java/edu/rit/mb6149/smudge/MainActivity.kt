package edu.rit.mb6149.smudge

import android.annotation.SuppressLint
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import edu.rit.mb6149.smudge.model.Artwork
import edu.rit.mb6149.smudge.model.BrushType
import edu.rit.mb6149.smudge.model.Tool
import edu.rit.mb6149.smudge.toolbars.BrushToolbar
import edu.rit.mb6149.smudge.toolbars.ColorPicker
import edu.rit.mb6149.smudge.toolbars.DownloadOptions
import edu.rit.mb6149.smudge.toolbars.EraserToolbar
import edu.rit.mb6149.smudge.toolbars.Layers
import edu.rit.mb6149.smudge.toolbars.PaintRollerToolbar
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                var selectedTool by remember { mutableStateOf(Tool.BRUSH) }

                var isColorsOpen by remember { mutableStateOf(false) }
                var isLayersOpen by remember { mutableStateOf(false) }
                var isDownloadOpen by remember { mutableStateOf(false) }
                var isToolbarOpen by remember { mutableStateOf(false) }

                var artwork by remember { mutableStateOf(Artwork("Test")) }
                var currentLayerPosition by remember { mutableIntStateOf(0) }

                var color by remember { mutableIntStateOf(Color.BLACK) }
                var strokeWidth by remember { mutableFloatStateOf(100f) }
                var style by remember { mutableStateOf(Paint.Style.STROKE) }
                var brushType by remember { mutableStateOf(BrushType.PEN) }
                var blendMode by remember { mutableStateOf(BlendMode.SRC_OVER) }

                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBar(
                            isColorsOpen = { updateIsColorsOpen ->
                                isColorsOpen = updateIsColorsOpen
                            },
                            isLayersOpen = { updateIsLayersOpen ->
                                isLayersOpen = updateIsLayersOpen
                            },
                            isDownloadOpen = { updateIsDownloadOpen ->
                                isDownloadOpen = updateIsDownloadOpen
                            }
                        ) },
                        bottomBar = { BottomAppBar(
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
                        ) },
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
        }
    }
}