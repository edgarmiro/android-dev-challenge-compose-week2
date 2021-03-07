/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(darkTheme = true) {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyApp() {
    var state by remember { mutableStateOf(State.STOPPED) }
    var text by remember { mutableStateOf("") }
    var remainingSeconds by remember(text) { mutableStateOf(text.toSeconds()) }
    val formattedRemainingSeconds by remember(remainingSeconds) { mutableStateOf(remainingSeconds.toHHMMss()) }

    fun stopCounter() {
        state = State.STOPPED
        text = ""
    }

    fun updateText(value: String) {
        if (text.length >= 6) return

        text += value
    }

    fun deleteText() {
        text = ""
    }

    fun removeText() {
        if (text.isEmpty()) return

        text = text.dropLast(1)
    }

    if (state == State.RUNNING) {
        LaunchedEffect(key1 = state) {
            while (remainingSeconds > 0) {
                delay(1000)
                remainingSeconds--
            }

            stopCounter()
        }
    }

    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            when (state) {
                State.STOPPED -> CountdownSelector(
                    text,
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    ::updateText,
                    ::deleteText,
                    ::removeText
                )
                State.RUNNING, State.PAUSED -> Text(
                    text = formattedRemainingSeconds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 45.sp
                )
            }

            if (state == State.RUNNING || state == State.PAUSED) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(40.dp),
                    onClick = { stopCounter() }) {
                    Icon(
                        imageVector = Icons.Filled.Stop,
                        contentDescription = "Stop"
                    )
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimatedVisibility(
                    visible = text.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CountdownButton(
                        state,
                        onClick = {
                            state = when (state) {
                                State.RUNNING -> State.PAUSED
                                State.STOPPED, State.PAUSED -> State.RUNNING
                            }
                        })
                }
            }
        }
    }
}

@Composable
private fun CountdownSelector(
    text: String,
    modifier: Modifier,
    onChangeText: (String) -> Unit,
    onDelete: () -> Unit,
    onRemove: () -> Unit
) {
    val formattedText by remember(text) { mutableStateOf(text.toHHMMss()) }

    Column(modifier = modifier) {
        Text(
            text = formattedText,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 45.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InputButton("1", onClick = { onChangeText("1") })
            InputButton("2", onClick = { onChangeText("2") })
            InputButton("3", onClick = { onChangeText("3") })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InputButton("4", onClick = { onChangeText("4") })
            InputButton("5", onClick = { onChangeText("5") })
            InputButton("6", onClick = { onChangeText("6") })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InputButton("7", onClick = { onChangeText("7") })
            InputButton("8", onClick = { onChangeText("8") })
            InputButton("9", onClick = { onChangeText("9") })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(onClick = onRemove) {
                Icon(imageVector = Icons.Filled.Backspace, contentDescription = "Backspace")
            }
            InputButton("0", onClick = { onChangeText("0") })
            FloatingActionButton(onClick = onDelete) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
private fun InputButton(text: String, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Text(text, fontSize = 20.sp)
    }
}

@Composable
private fun CountdownButton(state: State, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    FloatingActionButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = when (state) {
                State.RUNNING -> Icons.Filled.Pause
                State.STOPPED, State.PAUSED -> Icons.Filled.PlayArrow
            },
            contentDescription = when (state) {
                State.RUNNING -> "Pause"
                State.STOPPED, State.PAUSED -> "Play"
            }
        )
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

@Preview(widthDp = 56, heightDp = 56)
@Composable
fun ButtonPreview() {
    MyTheme(darkTheme = true) {
        CountdownButton(State.RUNNING)
    }
}

enum class State {
    RUNNING,
    PAUSED,
    STOPPED
}
