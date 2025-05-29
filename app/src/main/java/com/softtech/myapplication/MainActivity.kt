package com.softtech.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.softtech.myapplication.ui.compareimage.CompareImageScreen
import com.softtech.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //ChatScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                    //LoadPhotoScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                    CompareImageScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}
