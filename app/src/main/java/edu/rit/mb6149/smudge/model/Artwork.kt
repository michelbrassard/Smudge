package edu.rit.mb6149.smudge.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.ArtworkDTO

data class Artwork(
    var name: String,
    val layers: SnapshotStateList<Layer> = mutableStateListOf(Layer("Base Layer"))
) {
    constructor(dto: ArtworkDTO) : this(
        name = dto.name,
        layers = mutableStateListOf<Layer>().apply {
            dto.layers.forEach { add(Layer(it)) }
        }
    )
    fun toDTO() = ArtworkDTO(this)
}