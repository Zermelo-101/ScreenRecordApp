package com.github.zermelo101.screenrecorderapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


class RecordedFile : ComponentActivity() {
    data class Pos(val x : Float, val y : Float, val time : Long)

    data class Recording(val PhoneModel:String, val duration: Long ,val screenW: Int,val screenH : Int,val listPos : MutableList<Pos>)
    var startTime :Long = 0;
    private val list : MutableList<Pos> = mutableListOf()
    private val db: DatabaseReference = Firebase.database.reference
    var size : CompletableFuture<Int> = CompletableFuture()
    private var Ip = ""
    private  var maxX :Int =0
    private  var maxY :Int =0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ip = intent.getStringExtra("Ip").toString()

        Thread(Runnable {

            try{
                val ss : Socket = Socket(Ip,8080)
                val printWritter : PrintWriter = PrintWriter(ss.getOutputStream())

                printWritter.println("Start code")
                printWritter.close()
                ss.close()
            }
            catch (e : IOException){

            }
        }).start()


        setContent{
            SetUpScreenSize()
            startTime = System.nanoTime()
        throwMatrixActivty()
        }

    }

    override fun onPause() {
        super.onPause()
        val recording = Recording(Build.MANUFACTURER + " : " + Build.MODEL, System.nanoTime() - startTime, screenW = maxX, screenH = maxY,list)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateFormated = dateFormat.format(Calendar.getInstance().time)
        db.child("LogContinuous").child(dateFormated).setValue(recording)


        Thread(Runnable {

            try{
                val ss : Socket = Socket(Ip,8080)
                val printWritter : PrintWriter = PrintWriter(ss.getOutputStream())

                printWritter.println("End code")
                printWritter.close()
                ss.close()
            }
            catch (e : IOException){

            }
        }).start()


        /*
        Log.d("SCREEN SIZE",maxX.toString() +" "+ maxY.toString())
        Log.d("RECORDING",list.toString())
        Log.d("SIZE","size is  ${list.size}")

         */

    }
    @Composable
    fun ExitButton() {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = {
                    val x = Intent(this,MainActivity::class.java)
        startActivity(x)}),
            contentAlignment = Alignment.TopEnd
        ) {

        }
    }

    @Composable
    fun SetUpScreenSize(){
        val config = LocalConfiguration.current
        val density = LocalDensity.current
        maxX = with(density) {config.screenWidthDp.dp.roundToPx()}
        maxY = with(density) {config.screenHeightDp.dp.roundToPx()}

        // Log.d("SCREEN SIZE",maxX.toString() +" "+ maxY.toString())
    }
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun throwMatrixActivty() {




        var touchX by remember { mutableStateOf(0f) }
        var touchY by remember { mutableStateOf(0f) }
        val context = LocalContext.current

        ExitButton()
        Column {
            Text(
                text = "EXIT",
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {  val x = Intent(context,MainActivity::class.java)
                        startActivity(x) }

            )
            Text(text = "Touch X: $touchX Touch Y: $touchY")



            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter {
                        list.add(Pos(it.x, it.y, (System.nanoTime() - startTime)))
                        touchX = it.x
                        touchY = it.y
                        true
                    }
            )

        }
    }









}