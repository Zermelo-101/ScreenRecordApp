package com.github.zermelo101.screenrecorderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter


class RecordedFile : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
        throwMatrixActivty()
        }

    }
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun throwMatrixActivty() {

        var touchX by remember { mutableStateOf(0f) }
        var touchY by remember { mutableStateOf(0f) }


        Column {
            Text(text = "Touch X: $touchX")
            Text(text = "Touch Y: $touchY")

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter {
                        touchX = it.x
                        touchY = it.y
                        true
                    }
            )

        }
    }









}