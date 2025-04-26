package edu.rit.mb6149.smudge

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.canvas.CanvasPage
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.rit.mb6149.smudge.gallery.GalleryPage
import edu.rit.mb6149.smudge.model.Artwork

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Navigation(navController: NavHostController) {
    var currentArtwork: Artwork? by remember { mutableStateOf<Artwork?>(null) }
    val artworks: SnapshotStateList<Artwork> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        Storage.load(artworks) // This should return a list of Artwork
    }

    NavHost(navController, startDestination = "gallery") {
        composable("gallery") {
            GalleryPage(
                navController,
                updateCurrentArtwork = { updateArtwork ->
                    currentArtwork = updateArtwork
                },
                currentArtworks = artworks
            )
        }
        composable("canvas") {
            if (currentArtwork != null) {
                CanvasPage(navController, currentArtwork!!)
            } else {
                navController.navigate("gallery") {
                    popUpTo("gallery") { inclusive = true }
                }
            }
        }
    }
}