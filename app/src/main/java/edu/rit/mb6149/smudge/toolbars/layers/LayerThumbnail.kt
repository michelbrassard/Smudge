package edu.rit.mb6149.smudge.toolbars.layers

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.model.Layer

@Composable
fun LayerThumbnail(layer: Layer) {
    Canvas(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
    ) {
        val rectContainingAllPaths = layer.drawPaths
            .map { it.path.getBounds() }
            .fold(Rect.Zero) { current, rect -> current.combine(rect) }

        val scaleX = (size.width) / rectContainingAllPaths.width
        val scaleY = (size.height) / rectContainingAllPaths.height
        val scale = minOf(scaleX, scaleY)

        //certain elements disappear because they become too small to show on the canvas
        withTransform({
            translate(-rectContainingAllPaths.width / 2 * scale)
            scale(scale, scale)
        }) {
            val bounds = RectF(0f, 0f, size.width, size.height)
            drawIntoCanvas { canvas ->
                val layerId = canvas.nativeCanvas.saveLayer(bounds, null)
                layer.drawPaths.forEach { drawPath ->
                    val paint = Paint().apply {
                        color = drawPath.color
                        strokeWidth = drawPath.strokeWidth
                        style = drawPath.style
                        strokeCap = drawPath.brushType.strokeCap
                        maskFilter = drawPath.brushType.maskFilter
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawPath(drawPath.path.asAndroidPath(), paint)
                }
                canvas.nativeCanvas.restoreToCount(layerId)
            }
        }
    }
}

fun Rect.combine(otherRect: Rect): Rect {
    if (this == Rect.Zero) return otherRect
    if (otherRect == Rect.Zero) return this


    //get the furthest left point
    //left goes "more" left, top "more" to top ...
    val left = minOf(this.left, otherRect.left)
    val top = minOf(this.top, otherRect.top)
    val right = maxOf(this.right, otherRect.right)
    val bottom = maxOf(this.bottom, otherRect.bottom)

    return Rect(left, top, right, bottom)
}