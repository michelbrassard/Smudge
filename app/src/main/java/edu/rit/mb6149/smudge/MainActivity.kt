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
import androidx.compose.ui.Modifier
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmudgeTheme {
        DrawingCanvas()
    }
}