package edu.rit.mb6149.smudge

import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.ArtworkDTO
import edu.rit.mb6149.smudge.model.Artwork
import kotlinx.serialization.json.Json

class Storage {
    companion object {
        /**
         * Load artworks stored on the device
         */
        fun load(artworks: SnapshotStateList<Artwork>) {
            println("Loaded $artworks")
//            artworks.clear()
//            artworks.addAll(loadedArtworks)
            //populate from storage using DTOs
            //val fromJSonArtwork = Json.decodeFromString<ArtworkDTO>(jsonString).toObject()
        }

        /**
         * Create or update an artwork file
         */
        fun save(artwork: Artwork) {
            val jsonString = Json.encodeToString(artwork.toDTO())
            println("Saved: $jsonString")
        }

        /**
         * Delete artwork
         */
        fun remove(artwork: Artwork) {
            println("Deleted $artwork")
            //delete file
        }
    }
}