package edu.rit.mb6149.smudge

import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.BlurMaskFilter
import android.graphics.Shader
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
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import kotlin.random.Random

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
                path.fillType = PathFillType.NonZero
                drawPath(
                    path,
                    color = Color.hsl(
                        hue = 200f, //the color
                        saturation = 0.7f, //how gray it is
                        lightness = 0.5f, //white to black
                    ),
                    style = Stroke(
                        width = 20f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    ),
                )
                //For coloring? TODO
//                drawPath(path, brush = Brush.sweepGradient(
//                        0.0f to Color. Red,
//                        0.3f to Color. Green,
//                        1.0f to Color. Blue,
//                        center = Offset(0.0f, 100.0f)
//                    )
//                )

            //This is another tool!
//                drawIntoCanvas { canvas ->
//                    val paint = android.graphics.Paint().apply {
//                        color = android.graphics.Color.RED
//                        alpha = 100 // Keep it slightly transparent
//                        strokeWidth = 40f // Maintain line width
//                        style = android.graphics.Paint.Style.STROKE // Ensure it's a line, not a fill
//                        strokeCap = android.graphics.Paint.Cap.ROUND // Smooth, round edges
//                        maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL) // Softer blur
//                        isAntiAlias = true // Smooth rendering
//                    }
//                    canvas.nativeCanvas.drawPath(path.asAndroidPath(), paint)
//                }


            }
        }
    }
}