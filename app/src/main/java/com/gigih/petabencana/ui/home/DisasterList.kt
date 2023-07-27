package com.gigih.petabencana.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigih.petabencana.R
import com.gigih.petabencana.components.CardDisaster
import com.gigih.petabencana.data.GeometriesItem
import com.google.android.gms.maps.model.LatLng

@Composable
fun DisasterList(
    bencanaList: List<GeometriesItem>,
    onCardClicked: (LatLng) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    )  {
        Text(
            text = stringResource(R.string.bencana_terkini),
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
                    description = bencana.properties?.text.toString(),
                    onClick = {
                        // Call the click handler with the selected location's coordinates
                        val coordinates = LatLng(bencana.coordinates?.get(1) ?: 0.0,
                            bencana.coordinates?.get(0) ?: 0.0
                        )
                        onCardClicked(coordinates)
                    }
                )
            }
        }

    }
}