package edu.rit.mb6149.smudge.model

import android.graphics.Paint
import android.graphics.BlendMode
import androidx.compose.ui.graphics.Path

class DrawPath(
    var path: Path,
    var color: Int,
    var strokeWidth: Float,
    var style: Paint.Style,
    var brushType: BrushType,
    var blendMode: BlendMode
)