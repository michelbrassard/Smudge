package edu.rit.mb6149.smudge.dto

import edu.rit.mb6149.smudge.model.Artwork
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDTO(
    val fileName: String,
    val name: String,
    val layers: List<LayerDTO>
) {
    constructor(artwork: Artwork) : this(
        fileName = artwork.fileName,
        name = artwork.name,
        layers = artwork.layers.map { layer -> LayerDTO(layer) }
    )

    fun toObject() = Artwork(this)
}