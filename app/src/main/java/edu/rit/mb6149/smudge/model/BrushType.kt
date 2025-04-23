package edu.rit.mb6149.smudge.model

import android.graphics.BlurMaskFilter
import android.graphics.Paint

enum class BrushType(
    val brushName: String,
    val strokeCap: Paint.Cap,
    val maskFilter: BlurMaskFilter
) {
    PEN("Pen", Paint.Cap.ROUND, BlurMaskFilter(2f, BlurMaskFilter.Blur.NORMAL)),
    MARKER("Marker", Paint.Cap.SQUARE, BlurMaskFilter(0.1f, BlurMaskFilter.Blur.SOLID)),
    AIRBRUSH("Airbrush", Paint.Cap.ROUND, BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)),
    GLOW("Glow", Paint.Cap.ROUND, BlurMaskFilter(50f, BlurMaskFilter.Blur.OUTER))
}