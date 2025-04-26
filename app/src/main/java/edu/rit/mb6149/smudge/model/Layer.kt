package edu.rit.mb6149.smudge.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.LayerDTO

data class Layer(
    var name: String,
    val drawPaths: SnapshotStateList<DrawPath> = mutableStateListOf(),
    //val undoPaths: SnapshotStateList<DrawPath> = mutableStateListOf(),
    //val redoPaths: SnapshotStateList<DrawPath> = mutableStateListOf()
) {
    constructor(dto: LayerDTO) : this(
        name = dto.name,
        drawPaths = mutableStateListOf<DrawPath>().apply {
            dto.drawPaths.forEach { add(DrawPath(it)) }
        }
    )
    fun toDTO() = LayerDTO(this)
}