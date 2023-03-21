package com.github.zermelo101.screenrecorderapp



import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Please select Recording technique")
                Spacer(modifier = Modifier.width(50.dp))
                CreateRecordingtechRow()
            }

        }
    }


    @Composable
    fun CreateRecordingtechRow() {
        Row()
        {
            ButtonSplitactivity()
            Spacer(modifier = Modifier.width(10.dp))
            ButtonMAtrixActivity()
        }
    }

    @Composable
    fun ButtonSplitactivity() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            onClick = { throwSplitActivity() },
            modifier = Modifier
                .height(64.dp)
                .width(128.dp)
        )

        {
            Text(text = "Split")
        }
    }

    @Composable
    fun ButtonMAtrixActivity() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            onClick = { throwMatrixActivty() },
            modifier = Modifier
                .height(64.dp)
                .width(128.dp)
        )

        {
            Text(text = "Continuous")
        }
    }

    private fun throwSplitActivity() {
        val intent = Intent(this, SplitActivity::class.java)
        startActivity(intent)
    }

    private fun throwMatrixActivty(){
        val intent = Intent(this, RecordedFile::class.java)
        startActivity(intent)
    }



}
