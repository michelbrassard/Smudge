package edu.rit.mb6149.smudge.dto

import androidx.compose.ui.graphics.toSvg
import edu.rit.mb6149.smudge.model.DrawPath
import kotlinx.serialization.Serializable

@Serializable
data class DrawPathDTO(
    val path: String,
    val color: Int,
    val strokeWidth: Float,
    val style: String,
    val brushType: String,
    val blendMode: String
) {
    constructor(drawPath: DrawPath) : this(
        path = drawPath.path.toSvg(),
        color = drawPath.color,
        strokeWidth = drawPath.strokeWidth,
        style = drawPath.style.name,
        brushType = drawPath.brushType.name,
        blendMode = drawPath.blendMode.name
    )

    fun toObject() = DrawPath(this)
}