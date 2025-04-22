package edu.rit.mb6149.smudge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
    initialColor: Int
) {
    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HsvColorPicker(
            modifier = Modifier
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(32.dp),
            controller = controller,
            initialColor = Color(initialColor)
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(32.dp),
            controller = controller,
            initialColor = Color(initialColor)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {
            AlphaTile(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                controller = controller
            )
        }
    }
}