package com.gigih.petabencana.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CardDisaster(
    img: String?,
    disaster_type: String?,
    description: String,
    onClick: () -> Unit
) {
    val imageUrls = "https://bpsdm.dephub.go.id/v2/public/file_app/struktur_image/default.png"

    // Load the image using the Painter API from the URL
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .width(453.dp)
            .height(110.dp)
            .clickable(onClick = onClick)
    ) {
        Row {
            // Display the loaded image
            Image(
                painter = rememberImagePainter(
                    data = img ?: imageUrls,
//                builder = {
//                    // Optionally, you can set some parameters here, such as placeholder and error drawable
//                    error(Color.Red)
//                }
                ),
                contentDescription = "Image of disaster",
                modifier = Modifier
                    .height(110.dp)
                    .width(160.dp)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .align(CenterVertically)
            ) {
                Text(text = disaster_type ?: "tidak ada judul")
                Text(
                    text = description,
                    color = Color.Gray
                )
            }
        }
    }
}
