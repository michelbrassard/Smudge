package edu.rit.mb6149.smudge.canvas.toolbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    customHeight: Dp,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(customHeight),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier.Companion
                    .fillMaxSize(),
                contentAlignment = Alignment.Companion.Center
            ) {
                content()
            }
        }
    }
}