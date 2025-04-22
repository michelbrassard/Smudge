package edu.rit.mb6149.smudge

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Layers(
    updateIsLayersOpen: (Boolean) -> Unit
) {
    MinimalDialog(
        onDismissRequest = { updateIsLayersOpen(false) },
        customHeight = 200.dp
    ) {
        Column {
            Text("Layers...")
        }
    }
}