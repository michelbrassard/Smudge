package edu.rit.mb6149.smudge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.rit.mb6149.smudge.ui.theme.SmudgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmudgeTheme {
                var isBrushesOpen by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBar() },
                        bottomBar = { BottomAppBar(
                            isBrushesOpen = { updateIsBrushesOpen ->
                                isBrushesOpen = updateIsBrushesOpen
                            }
                        ) },
                    ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        DrawingCanvas()
                    }
                }

                when {
                    isBrushesOpen -> {
                        MinimalDialog(
                            onDismissRequest = { isBrushesOpen = false }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmudgeTheme {
        DrawingCanvas()
    }
}