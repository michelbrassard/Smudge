package edu.rit.mb6149.smudge.canvas.toolbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    pickedColor: (Int) -> Unit,
    initialColor: Int,
    updateIsColorsOpen: (Boolean) -> Unit
) {
    val controller = rememberColorPickerController()
    MinimalDialog(
        onDismissRequest = { updateIsColorsOpen(false) },
        customHeight = 550.dp,
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HsvColorPicker(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    pickedColor(colorEnvelope.color.toArgb())
                },
                initialColor = Color(initialColor)
            )
            AlphaSlider(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(32.dp),
                controller = controller,
                initialColor = Color(initialColor)
            )
            BrightnessSlider(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(32.dp),
                controller = controller,
                initialColor = Color(initialColor)
            )
            Column(
                modifier = Modifier.Companion.fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Row {
                    AlphaTile(
                        modifier = Modifier.Companion
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        controller = controller
                    )
                }

            }
        }
    }

}