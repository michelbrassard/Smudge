package edu.rit.mb6149.smudge.gallery

import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.rit.mb6149.smudge.model.Artwork

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GalleryItem(
    navController: NavHostController,
    artwork: Artwork,
    index: Int,
    updateCurrentArtwork: (Artwork) -> Unit,
    showDeleteDialog: (Boolean) -> Unit,
    updateDeleteIndex: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .combinedClickable(
                onClick = {
                    updateCurrentArtwork(artwork)
                    navController.navigate("canvas")
                },
                onLongClick = {
                    updateDeleteIndex(index)
                    showDeleteDialog(true)
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
        ) {
            val bounds = RectF(0f, 0f, size.width, size.height)
            drawIntoCanvas { canvas ->
                artwork.layers.forEach { layer ->
                    val layerId = canvas.nativeCanvas.saveLayer(bounds, null)
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
                        canvas.nativeCanvas.drawPath(drawPath.path.asAndroidPath(), paint)
                    }
                    canvas.nativeCanvas.restoreToCount(layerId)
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = artwork.name,
        )
    }
}