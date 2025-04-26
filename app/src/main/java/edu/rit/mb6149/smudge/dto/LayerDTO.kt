package edu.rit.mb6149.smudge.dto

import edu.rit.mb6149.smudge.model.Layer
import kotlinx.serialization.Serializable

@Serializable
data class LayerDTO(
    val name: String,
    val drawPaths: List<DrawPathDTO>
) {
    constructor(layer: Layer) : this(
        name = layer.name,
        drawPaths = layer.drawPaths.map { drawPath -> DrawPathDTO(drawPath) }
    )

    fun toObject() = Layer(this)
}