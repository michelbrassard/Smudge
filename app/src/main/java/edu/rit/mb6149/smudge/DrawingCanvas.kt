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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun DrawingCanvas() {
    val strokePaths = remember {
        mutableStateListOf<Path>()
    }
    var currentPath by remember {
        mutableStateOf<Path?>(null)
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
                        currentPath = Path().apply { moveTo(offset.x, offset.y) }
                        strokePaths.add(currentPath!!)
                        lastPoint = offset
                    },
                    onDrag = { change, _ ->
                        lastPoint?.let { last ->
                            currentPath?.quadraticTo(
                                last.x, last.y,
                                (last.x + change.position.x) / 2,
                                (last.y + change.position.y) / 2
                            )
                        }
                        strokePaths.removeAt(strokePaths.lastIndex)
                        strokePaths.add(currentPath!!)
                        lastPoint = change.position
                    },
                    onDragEnd = {
                        lastPoint = null
                        currentPath = null
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            strokePaths.forEach { path ->
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        alpha = 255 //0..255
                        strokeWidth = 10f
                        style = android.graphics.Paint.Style.STROKE
                        strokeCap = android.graphics.Paint.Cap.ROUND
                        maskFilter = BlurMaskFilter(2f, BlurMaskFilter.Blur.NORMAL) // Softer blur
                        isAntiAlias = true // Smooth rendering
                    }
                    canvas.nativeCanvas.drawPath(path.asAndroidPath(), paint)
                }
            }
        }
    }
}