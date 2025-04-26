package edu.rit.mb6149.smudge.model

import android.graphics.Paint
import android.graphics.BlendMode
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.PathParser
import edu.rit.mb6149.smudge.dto.DrawPathDTO

data class DrawPath(
    var path: Path,
    var color: Int,
    var strokeWidth: Float,
    var style: Paint.Style,
    var brushType: BrushType,
    var blendMode: BlendMode
) {
    constructor(dto: DrawPathDTO) : this(
        path = PathParser().parsePathString(dto.path ).toPath(),
        color = dto.color,
        strokeWidth = dto.strokeWidth,
        style = Paint.Style.valueOf(dto.style),
        brushType = BrushType.valueOf(dto.brushType),
        blendMode = BlendMode.valueOf(dto.blendMode)
    )
    fun toDTO() = DrawPathDTO(this)
}