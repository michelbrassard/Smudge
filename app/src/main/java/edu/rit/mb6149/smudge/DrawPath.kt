package edu.rit.mb6149.smudge

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.Path

class DrawPath(
    var path: Path,
    var color: Int,
    var strokeWidth: Float,
    var style: android.graphics.Paint.Style,
    var strokeCap: android.graphics.Paint.Cap,
    var maskFilter: BlurMaskFilter //high number for
)