package com.softtech.myapplication.ui.loadphoto

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadPhotoScreen(modifier: Modifier = Modifier, viewModel: LoadPhotoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
                viewModel.setSelectedImageBitmapData(bitmap)
                viewModel.onUriSelection(it)
            }
        }
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(48.dp)
        ) {
            viewModel.selectedImageUri.value?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(size = 240.dp)
                        .padding(vertical = 24.dp),
                    contentScale = ContentScale.Crop
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.suggestionText.value,
                    onValueChange = viewModel::onSuggestionTextChanged
                )
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = viewModel::onSuggestionClicked
                ) {
                    Text(text = "Suggestion")
                }

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = viewModel.contentText.value)
                }

            }
        }

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { galleryLauncher.launch("image/*") }
        ) {
            Text(text = "Pick image")
        }
    }
}