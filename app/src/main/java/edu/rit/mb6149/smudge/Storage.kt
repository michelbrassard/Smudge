package edu.rit.mb6149.smudge

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.ArtworkDTO
import edu.rit.mb6149.smudge.model.Artwork
import kotlinx.serialization.json.Json

class Storage {
    companion object {
        /**
         * Load artworks stored on the device
         */
        fun load(context: Context, artworks: SnapshotStateList<Artwork>) {
            artworks.clear()
            context.fileList()
                .filter { it != "profileInstalled" }
                .forEach {
                    val fileName = it
                    val jsonString = try {
                        context.openFileInput(fileName).bufferedReader().use { it.readText() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                        return
                    }
                    artworks.add(Json.decodeFromString<ArtworkDTO>(jsonString).toObject())
                }
        }

        /**
         * Create or update an artwork file
         */
        fun save(context: Context, artwork: Artwork) {
            val jsonString = Json.encodeToString(artwork.toDTO())
            context.openFileOutput(artwork.fileName, Context.MODE_PRIVATE).use { output ->
                output.write(jsonString.toByteArray())
            }
        }

        /**
         * Delete artwork
         */
        fun remove(context: Context, artwork: Artwork) {
            val success = context.deleteFile(artwork.fileName)
            if (!success) {
                println("File not found")
            }
        }
    }
}