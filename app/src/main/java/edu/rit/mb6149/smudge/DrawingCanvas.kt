package edu.rit.mb6149.smudge

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

@Composable
fun DrawingCanvas(
    currentColor: Int,
    currentStrokeWidth: Float,
    currentStyle: android.graphics.Paint.Style,
    currentStrokeCap: android.graphics.Paint.Cap,
    currentMaskFilter: BlurMaskFilter
) {
    val updatedColor by rememberUpdatedState(currentColor)
    val drawPathPaths = remember {
        mutableStateListOf<DrawPath>()
    }
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
                            currentStrokeWidth,
                            currentStyle,
                            currentStrokeCap,
                            currentMaskFilter
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
            drawPathPaths.forEach { drawPoint ->
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        color = drawPoint.color
                        strokeWidth = drawPoint.strokeWidth
                        style = drawPoint.style
                        strokeCap = drawPoint.strokeCap
                        maskFilter = drawPoint.maskFilter
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawPath(drawPoint.path.asAndroidPath(), paint)
                }
            }
        }
    }
}