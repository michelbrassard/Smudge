package edu.rit.mb6149.smudge

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                //top app bar
                var isColorsOpen by remember { mutableStateOf(false) }
                var isLayersOpen by remember { mutableStateOf(false) }
                var isDownloadOpen by remember { mutableStateOf(false) }
                //layers -> list of path lists

                //bottom app bar
                var isBrushesOpen by remember { mutableStateOf(false) }

                var color by remember { mutableIntStateOf(Color.BLACK) }
                var strokeWidth by remember { mutableFloatStateOf(10f) }
                var style by remember { mutableStateOf(Paint.Style.STROKE) }
                var strokeCap by remember { mutableStateOf(Paint.Cap.ROUND) }
                var maskFilter by remember { mutableStateOf(BlurMaskFilter(2f, BlurMaskFilter.Blur.NORMAL)) }

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
                            isBrushesOpen = { updateIsBrushesOpen ->
                                isBrushesOpen = updateIsBrushesOpen
                            }
                        ) },
                    ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        DrawingCanvas(color, strokeWidth, style, strokeCap, maskFilter)
                    }
                }

                when {
                    //Top App Bar functions
                    isColorsOpen -> {
                        MinimalDialog(
                            onDismissRequest = { isColorsOpen = false },
                            customHeight = 580.dp,
                        ) {
                            ColorPicker(
                                pickedColor = { updateColor ->
                                    color = updateColor
                                },
                                initialColor = color
                            )
                        }
                    }
                    isLayersOpen -> {
                        MinimalDialog(
                            onDismissRequest = { isLayersOpen = false },
                            customHeight = 200.dp
                        ) {
                            Text("Layers...")
                        }
                    }
                    isDownloadOpen -> {
                        MinimalDialog(
                            onDismissRequest = { isDownloadOpen = false },
                            customHeight = 200.dp
                        ) {
                            Text("Download...")
                        }
                    }

                    //Bottom App Bar functions
                    isBrushesOpen -> {
                        MinimalDialog(
                            onDismissRequest = { isBrushesOpen = false },
                            customHeight = 200.dp
                        ) {
                            Column {
                                Text("Brushes...")
                            }

                        }
                    }
                }
                //TOP BAR...
                //is layers
                //is download

                //BOTTOM BAR
                //is primary button
            }
        }
    }
}