package edu.rit.mb6149.smudge.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Artwork(
    var name: String,
    val layers: SnapshotStateList<Layer> = mutableStateListOf(Layer("Layer 1"))
)

class Layer(
    var name: String,
    val drawPaths: SnapshotStateList<DrawPath> = mutableStateListOf()
)