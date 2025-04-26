package edu.rit.mb6149.smudge.dto

import edu.rit.mb6149.smudge.model.Artwork
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDTO(
    val name: String,
    val layers: List<LayerDTO>
) {
    constructor(artwork: Artwork) : this(
        name = artwork.name,
        layers = artwork.layers.map { layer -> LayerDTO(layer) }
    )

    fun toObject() = Artwork(this)
}