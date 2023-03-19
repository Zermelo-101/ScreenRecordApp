package com.github.zermelo101.screenrecorderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

import com.google.firebase.database.ktx.database
import java.io.File


class SplitActivity : ComponentActivity() {
    data class LogType(val time : Long,val pos : String)
    var startTime :Long = 0;
    var size : Int = 0
    private val db: DatabaseReference = Firebase.database.reference
    private var list : MutableList<LogType> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.child("Log").get().addOnSuccessListener {
            size = (it.value as List<*>).size

        }
        setContent {
            CreateTwoBigButtons()
            startTime = System.nanoTime()

        }
    }

    override fun onPause() {
        super.onPause()

        db.child("Log").child(size.toString()).setValue(list.toString())


    }

    @Preview
    @Composable
    fun CreateTwoBigButtons(){
        Column(modifier = Modifier.fillMaxSize())

        {
            Button(onClick = { storeUpOrDownInDatabase(false,System.nanoTime() - startTime) },
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .fillMaxHeight(0.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray))
            {
                Text(text = "TOP ")
            }
            Button(onClick = {storeUpOrDownInDatabase(true,System.nanoTime() - startTime) },
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .fillMaxHeight(1.0f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray))
            {
                Text(text = "DOWN ")
            }
        }
    }

    private fun storeUpOrDownInDatabase(bool:Boolean , time :Long ){
        if (!bool){
            this.list.add(LogType(time,"UP"))
        }
        else {
            this.list.add(LogType(time,"DOWN"))
        }

    }

}

