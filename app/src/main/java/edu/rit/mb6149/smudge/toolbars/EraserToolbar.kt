package edu.rit.mb6149.smudge.toolbars

import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.toolbars.MinimalDialog
import edu.rit.mb6149.smudge.model.BrushType

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun EraserToolbar(
    updateIsToolbarOpen: (Boolean) -> Unit,
    updateStrokeWidth: (Float) -> Unit,
    updateBrushType: (BrushType) -> Unit,
    currentStrokeWidth: Float,
    currentBrush: BrushType
) {
    val updatedStrokeWidth by rememberUpdatedState(currentStrokeWidth)
    val updatedBrushType by rememberUpdatedState(currentBrush)

    val brushes = BrushType.entries.toList()
    var expanded by remember { mutableStateOf(false) }

    MinimalDialog(
        onDismissRequest = { updateIsToolbarOpen(false) },
        customHeight = 500.dp
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                val bounds = RectF(0f, 0f, size.width, size.height)
                drawIntoCanvas { canvas ->
                    val layerId = canvas.nativeCanvas.saveLayer(bounds, null)
                    val rectangleBackground = Paint().apply {
                        color = Color.BLACK
                        style = Paint.Style.FILL
                        blendMode = BlendMode.SRC_OVER
                    }
                    val referenceRectangle = RectF(0f, 0f, size.width, size.height)
                    canvas.nativeCanvas.drawRect(referenceRectangle, rectangleBackground)

                    val paint = Paint().apply {
                        color = Color.BLACK
                        strokeWidth = updatedStrokeWidth
                        style = Paint.Style.STROKE
                        strokeCap = updatedBrushType.strokeCap
                        maskFilter = updatedBrushType.maskFilter
                        blendMode = BlendMode.CLEAR
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawPoint(size.width / 2, size.height / 2, paint)
                    canvas.nativeCanvas.restoreToCount(layerId)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                updatedStrokeWidth,
                onValueChange = {
                        newValue -> updateStrokeWidth(newValue)
                },
                valueRange = 1f..500f
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Tool: ")
                    TextButton(onClick = { expanded = true }) {
                        Text(updatedBrushType.brushName)
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    brushes.forEach { brush ->
                        DropdownMenuItem(
                            text = { Text(brush.brushName) },
                            onClick = {
                                updateBrushType(brush)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}