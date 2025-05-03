package edu.rit.mb6149.smudge.canvas.toolbars.layers

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.model.Layer
import androidx.core.graphics.createBitmap

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LayerThumbnail(layer: Layer) {
    Canvas(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
    ) {
        if (layer.drawPaths.isEmpty()) {
            return@Canvas
        }
        val bitmap = drawLayerToBitmap(layer)

        val scaleX = size.width / bitmap.width
        val scaleY = size.height / bitmap.height
        val scale = minOf(scaleX, scaleY)

        drawIntoCanvas { canvas ->
            val destRect = RectF(0f, 0f, bitmap.width * scale, bitmap.height * scale)
            canvas.nativeCanvas.drawBitmap(bitmap, null, destRect, null)
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

@RequiresApi(Build.VERSION_CODES.Q)
fun drawLayerToBitmap(
    layer: Layer,
): Bitmap {
    val rectContainingAllPaths = layer.drawPaths
        .map { it.path.getBounds() }
        .fold(Rect.Zero) { current, rect -> current.combine(rect) }

    val bitmap = createBitmap(rectContainingAllPaths.width.toInt(), rectContainingAllPaths.height.toInt())
    val canvas = android.graphics.Canvas(bitmap)

    val bounds = RectF(0f, 0f, rectContainingAllPaths.width, rectContainingAllPaths.height)
    val layerId = canvas.saveLayer(bounds, null)
    layer.drawPaths.forEach { drawPath ->
        val paint = Paint().apply {
            color = drawPath.color
            strokeWidth = drawPath.strokeWidth
            style = drawPath.style
            strokeCap = drawPath.brushType.strokeCap
            maskFilter = drawPath.brushType.maskFilter
            isAntiAlias = true
            blendMode = drawPath.blendMode
            strokeJoin = Paint.Join.ROUND
        }
        canvas.drawPath(drawPath.path.asAndroidPath(), paint)
    }
    canvas.restoreToCount(layerId)

    return bitmap
}