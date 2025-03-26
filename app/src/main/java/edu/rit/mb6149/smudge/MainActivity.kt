package edu.rit.mb6149.smudge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBar() },
                        bottomBar = { BottomAppBar() },
                    ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        DrawingCanvas()
                    }

                }
            }
        }
    }
}

@Composable
fun DrawingCanvas() {
    val strokePaths = remember {
        mutableStateListOf<Path>()
    }
    var currentPath by remember {
        mutableStateOf<Path?>(null)
    }
    var lastPoint by remember {
        mutableStateOf<Offset?>(null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = Path().apply { moveTo(offset.x, offset.y) }
                        strokePaths.add(currentPath!!)
                        lastPoint = offset
                    },
                    onDrag = { change, _ ->
                        lastPoint?.let { last ->
                            currentPath?.quadraticTo(
                                last.x, last.y,
                                (last.x + change.position.x) / 2,
                                (last.y + change.position.y) / 2
                            )
                        }
                        strokePaths.add(currentPath!!)
                        lastPoint = change.position
                    },
                    onDragEnd = {
                        lastPoint = null
                        currentPath = null
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            strokePaths.forEach { path ->
                drawPath(
                    path,
                    color = Color.Black,
                    style = Stroke(width = 40f, cap = StrokeCap.Round))
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Description")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Description")
            }
        }
    )
}

@Composable
fun BottomAppBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Check, contentDescription = "Description")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Edit, contentDescription = "Description")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Menu, contentDescription = "Description")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Local description")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmudgeTheme {
        DrawingCanvas()
    }
}