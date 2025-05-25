package com.softtech.myapplication.ui.compareimage

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import kotlin.collections.get

@Composable
fun CompareImageScreen(modifier: Modifier,viewModel: CompareImageViewModel = viewModel()) {

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
                viewModel.addPhotoToList(uri, bitmap)
            }
        }
    )
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        LazyRow {
            items(viewModel.selectedPhotoList.toList().size) {
                Image(
                    painter = rememberAsyncImagePainter(model = viewModel.selectedPhotoList.toList()[it].first),
                    contentDescription = null,
                    modifier = androidx.compose.ui.Modifier
                        .size(140.dp)
                        .padding(24.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text(text = "Add Photo")
                }
            }
        }

        if (viewModel.selectedPhotoList.toList().size >= 2) {
            TextField(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                value = viewModel.requestText.value,
                onValueChange = viewModel::onRequestTextChangeListener
            )

            Button(onClick = viewModel::onCompareImages) {
                Text(text = "Request")
            }

            Text(text = viewModel.contentText.value)
        }


        if (viewModel.contentText.value.isEmpty().not()) {
            Button(onClick = viewModel::clearAll) {
                Text(text = "Clear")
            }
        }

    }
}