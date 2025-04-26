package edu.rit.mb6149.smudge.toolbars

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DownloadOptions(
    updateIsDownloadOpen: (Boolean) -> Unit
) {
    MinimalDialog(
        onDismissRequest = { updateIsDownloadOpen(false) },
        customHeight = 200.dp
    ) {
        Column {
            Text("Download...")
        }
    }
}