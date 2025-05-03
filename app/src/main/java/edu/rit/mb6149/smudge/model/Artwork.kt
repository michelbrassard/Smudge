package edu.rit.mb6149.smudge.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.ArtworkDTO
import java.util.UUID

data class Artwork(
    val fileName: String,
    var name: String,
    val layers: SnapshotStateList<Layer> = mutableStateListOf(Layer("Base Layer"))
) {
    constructor(dto: ArtworkDTO) : this(
        fileName = dto.fileName,
        name = dto.name,
        layers = mutableStateListOf<Layer>().apply {
            dto.layers.forEach { add(Layer(it)) }
        }
    )

    constructor(name: String) : this(
        fileName = "${UUID.randomUUID()}.json",
        name = name,
        layers = mutableStateListOf(Layer("Base Layer"))
    )

    fun toDTO() = ArtworkDTO(this)
}