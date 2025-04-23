package edu.rit.mb6149.smudge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import edu.rit.mb6149.smudge.model.Artwork
import edu.rit.mb6149.smudge.model.BrushType
import edu.rit.mb6149.smudge.model.DrawPath

@Composable
fun DrawingCanvas(
    currentColor: Int,
    currentStrokeWidth: Float,
    currentStyle: android.graphics.Paint.Style,
    currentBrushType: BrushType,
    currentArtwork: Artwork,
    currentLayerPosition: Int
) {
    val updatedColor by rememberUpdatedState(currentColor)
    val updatedStrokeWidth by rememberUpdatedState(currentStrokeWidth)
    val updatedStyle by rememberUpdatedState(currentStyle)
    val updatedBrushType by rememberUpdatedState(currentBrushType)
    val updatedArtwork by rememberUpdatedState(currentArtwork)
    val updatedLayerPosition by rememberUpdatedState(currentLayerPosition)

    val drawPathPaths by rememberUpdatedState(updatedArtwork.layers[updatedLayerPosition].drawPaths)
    var currentDrawPath by remember {
        mutableStateOf<DrawPath?>(null)
    }
    var lastPoint by remember {
        mutableStateOf<Offset?>(null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentDrawPath = DrawPath(
                            Path().apply { moveTo(offset.x, offset.y) },
                            updatedColor,
                            updatedStrokeWidth,
                            updatedStyle,
                            updatedBrushType
                        )

                        drawPathPaths.add(currentDrawPath!!)
                        lastPoint = offset
                    },
                    onDrag = { change, _ ->
                        lastPoint?.let { last ->
                            currentDrawPath?.path?.quadraticTo(
                                last.x, last.y,
                                (last.x + change.position.x) / 2,
                                (last.y + change.position.y) / 2
                            )
                        }
                        drawPathPaths.removeAt(drawPathPaths.lastIndex)
                        drawPathPaths.add(currentDrawPath!!)
                        lastPoint = change.position
                    },
                    onDragEnd = {
                        lastPoint = null
                        currentDrawPath = null
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            updatedArtwork.layers.forEach { layer ->
                layer.drawPaths.forEach { drawPath ->
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint().apply {
                            color = drawPath.color
                            strokeWidth = drawPath.strokeWidth
                            style = drawPath.style
                            strokeCap = drawPath.brushType.strokeCap
                            maskFilter = drawPath.brushType.maskFilter
                            isAntiAlias = true
                        }
                        canvas.nativeCanvas.drawPath(drawPath.path.asAndroidPath(), paint)
                    }
                }
            }
        }
    }
}