package edu.rit.mb6149.smudge.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import edu.rit.mb6149.smudge.dto.LayerDTO

data class Layer(
    var name: String,
    val drawPaths: SnapshotStateList<DrawPath> = mutableStateListOf(),
    val undoneDrawPaths: SnapshotStateList<DrawPath> = mutableStateListOf(),
) {
    constructor(dto: LayerDTO) : this(
        name = dto.name,
        drawPaths = mutableStateListOf<DrawPath>().apply {
            dto.drawPaths.forEach { add(DrawPath(it)) }
        },
        undoneDrawPaths = mutableStateListOf<DrawPath>().apply {
            dto.undoneDrawPaths.forEach { add(DrawPath(it)) }
        }
    )
    fun toDTO() = LayerDTO(this)
    fun undo() {
        if (drawPaths.isEmpty()) {
            return
        }
        try {
            val undoneDrawPath = drawPaths.last()
            undoneDrawPaths.add(undoneDrawPath)
            drawPaths.remove(undoneDrawPath)
        }
        catch (error: NoSuchElementException) {
            println(error)
        }
    }
    fun canPerformUndo(): Boolean {
        return drawPaths.isNotEmpty()
    }

    fun redo() {
        if (undoneDrawPaths.isEmpty()) {
            return
        }
        try {
            val redoneDrawPath = undoneDrawPaths.last()
            drawPaths.add(redoneDrawPath)
            undoneDrawPaths.remove(redoneDrawPath)
        }
        catch (error: NoSuchElementException) {
            println(error)
        }
    }
    fun canPerformRedo(): Boolean {
        return undoneDrawPaths.isNotEmpty()
    }

    /**
     * Reset undo/redo
     * Used when someone undoes a line and then starts drawing again
     */
    fun reset() {
        undoneDrawPaths.clear()
    }
}