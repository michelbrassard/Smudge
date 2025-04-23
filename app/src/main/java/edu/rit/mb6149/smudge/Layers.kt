package edu.rit.mb6149.smudge

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.model.Artwork
import edu.rit.mb6149.smudge.model.Layer
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun Layers(
    updateIsLayersOpen: (Boolean) -> Unit,
    updateLayerPosition: (Int) -> Unit,
    currentLayerPosition: Int,
    currentArtwork: Artwork
) {
    val updatedLayerPosition by rememberUpdatedState(currentLayerPosition)

    var layers = currentArtwork.layers
    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            layers = layers.apply {
                add(to.index, removeAt(from.index))
                updateLayerPosition(to.index)
            }
        }
    )

    MinimalDialog(
        onDismissRequest = { updateIsLayersOpen(false) },
        customHeight = 500.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                state = state.listState,
                modifier = Modifier
                    .reorderable(state)
                    .detectReorderAfterLongPress(state)
                    .padding(8.dp)
                    .height(400.dp)
                    .clip(RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(layers, key = { _, layer -> layer.hashCode() }) { index, item: Layer ->
                    ReorderableItem(state, key = item) { isDragging ->
                        val elevation by animateDpAsState(
                            if (isDragging) 16.dp else 0.dp, label = ""
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (index == updatedLayerPosition)
                                        MaterialTheme.colorScheme.secondaryContainer
                                    else
                                        MaterialTheme.colorScheme.surface
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        updateLayerPosition(index)
                                    }
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                //todo add layer names
                                //todo add canvas representing the layer
                                Text("Layer")
                                IconButton(onClick = {
                                    if (layers.size > 1) {
                                        layers = layers.apply {
                                            removeAt(index)
                                        }
                                        if (updatedLayerPosition > 0) {
                                            updateLayerPosition(index - 1)
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(onClick = {
                    layers.add(Layer("New Layer"))
                }) {
                    Text("Add")
                }
            }
        }
    }
}


