package com.gigih.petabencana.ui.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gigih.petabencana.data.GeometriesItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private const val TAG = "BasicMapActivity"

val singapore = LatLng(1.35, 103.87)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(singapore, 11f)

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    markerDataList: List<GeometriesItem> = emptyList(),
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
                    markerData.coordinates?.get(1) ?: 0.0,
                    markerData.coordinates?.get(0) ?: 0.0
                )
                val markerState = rememberMarkerState(position = position)
                Marker(
                    state = markerState,
                    // Set other properties of the marker as needed
                    // For example, you can set a title or icon for the marker
                    snippet = markerData.properties?.disasterType,
                    title = markerData.properties?.text ?: "",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
            }
            Log.d("mapsactivity", markerDataList.toString())


            content()
        }

    }
}