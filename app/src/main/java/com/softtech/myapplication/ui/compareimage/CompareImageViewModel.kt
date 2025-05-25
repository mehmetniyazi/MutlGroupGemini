package com.softtech.myapplication.ui.compareimage

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class CompareImageViewModel: ViewModel() {

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash", //gemini vision da olabilir.
        apiKey = "AIzaSyA3zZwx4EVRRN__dUZAxOEirN4vFgJbT6Y"
    )

    var selectedPhotoList = mutableStateListOf<Pair<Uri?, Bitmap?>>()
    var requestText = mutableStateOf("")
        private set

    var contentText = mutableStateOf("")

    fun onRequestTextChangeListener(text: String) {
        requestText.value = text
    }

    fun addPhotoToList(uri: Uri?, bitmap: Bitmap?) {
        selectedPhotoList.add(Pair(uri, bitmap))
    }

    fun clearAll() {
        selectedPhotoList.clear()
        contentText.value = ""
        requestText.value = ""
    }


    fun onCompareImages() {
        Log.d("generativeModelrequest", "onCompareImages")
        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(
                    content {
                        selectedPhotoList.toList().map {
                            image(it.second ?: return@content)
                        }
                        text(requestText.value)
                    }
                )
                Log.d("generativeModelrequest", "result on onCompareImages")
                contentText.value = response.text ?: "Ai coktu hata verdi bir seyler"
            } catch (e: Exception) {
                Log.d("generativeModelrequest", "error : ${e.localizedMessage}")
            }

        }.invokeOnCompletion {
            Log.d("generativeModelrequest", "onCompleted")
        }
    }
}