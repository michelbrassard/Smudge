package edu.rit.mb6149.smudge.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.rit.mb6149.smudge.model.Artwork

@Composable
fun GalleryItem(
    navController: NavHostController,
    artwork: Artwork,
    updateCurrentArtwork: (Artwork) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = artwork.name,
            Modifier.Companion.clickable {
                updateCurrentArtwork(artwork)
                navController.navigate("canvas")
            }
        )
    }
}