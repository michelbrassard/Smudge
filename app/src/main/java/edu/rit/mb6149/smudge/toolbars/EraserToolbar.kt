package edu.rit.mb6149.smudge.toolbars

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.MinimalDialog

@Composable
fun EraserToolbar(
    updateIsToolbarOpen: (Boolean) -> Unit
) {
    MinimalDialog(
        onDismissRequest = { updateIsToolbarOpen(false) },
        customHeight = 200.dp
    ) {
        Column {
            Text("Eraser Toolbar...")
        }
    }
}