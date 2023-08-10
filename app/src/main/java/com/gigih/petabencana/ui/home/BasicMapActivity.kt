package com.gigih.petabencana.ui.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gigih.petabencana.data.BencanaTable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private const val TAG = "BasicMapActivity"

val jakarta = LatLng(-6.2113664140089, 106.84790917979694)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(jakarta, 11f)

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    markerDataList: List<BencanaTable> = emptyList(),
    content: @Composable () -> Unit = {}
) {

    val uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val mapVisible by remember { mutableStateOf(true) }

    if (mapVisible) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = onMapLoaded,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.name}")
            }
        ) {
            markerDataList.forEach { markerData ->
                val position = LatLng(
                    markerData.lat ?: 0.0,
                    markerData.lng ?: 0.0
                )
                val markerState = rememberMarkerState(position = position)
                Marker(
                    state = markerState,
                    // Set other properties of the marker as needed
                    // For example, you can set a title or icon for the marker
                    snippet = markerData.type,
                    title = markerData.title ?: "",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            }
            Log.d("mapsactivity", markerDataList.toString())


            content()
        }

    }
}