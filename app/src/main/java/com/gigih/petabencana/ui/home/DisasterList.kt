package com.gigih.petabencana.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigih.petabencana.components.CardDisaster
import com.gigih.petabencana.data.GeometriesItem

@Composable
fun DisasterList(
    bencanaList: List<GeometriesItem>,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    )  {
//        Divider(
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .width(50.dp)
//                .height(5.dp)
//                .align(CenterHorizontally),
//
//        )
        Text(
//                text = stringResource(R.string.informasi_bencana),
            text = "Informasi Bencana Terkini",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        LazyColumn {
            items(bencanaList) { bencana ->
                CardDisaster(
                    img = bencana.properties?.imageUrl,
                    disaster_type = bencana.type,
                    description = bencana.properties?.text.toString()
                )
            }
        }

    }
}