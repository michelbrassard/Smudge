package edu.rit.mb6149.smudge.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.model.Artwork
import edu.rit.mb6149.smudge.canvas.toolbars.MinimalDialog

@Composable
fun DeleteArtworkConfirmationAlert(
    updateIsDeleteDialogOpen: (Boolean) -> Unit,
    currentArtworks: SnapshotStateList<Artwork>,
    currentArtwork: Artwork
) {
    val artworks by rememberUpdatedState(currentArtworks)
    val artwork by rememberUpdatedState(currentArtwork)
    MinimalDialog(
        onDismissRequest = { updateIsDeleteDialogOpen(false) },
        customHeight = 200.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Delete drawing",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Delete ${artwork.name}?",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White),
                    onClick = {
                        artworks.remove(artwork)
                        updateIsDeleteDialogOpen(false)
                    },

                ) {
                    Text("Delete", color = Color.White)
                }
                TextButton(
                    onClick = {
                        updateIsDeleteDialogOpen(false)
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}