package edu.rit.mb6149.smudge

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(24.dp)) {
                        Greeting(
                            name = "Smudge",
                            modifier = Modifier.padding(innerPadding)
                        )
                        DrawingCanvas()
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        modifier = modifier
    )
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
        Text(text = "Canvas")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmudgeTheme {
        Greeting("Android")
    }
}