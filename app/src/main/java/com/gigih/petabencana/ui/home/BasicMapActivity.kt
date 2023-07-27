package com.gigih.petabencana.ui.home

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gigih.petabencana.data.GeometriesItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private const val TAG = "BasicMapActivity"

val singapore = LatLng(1.35, 103.87)
val singapore2 = LatLng(1.40, 103.77)
val singapore3 = LatLng(1.45, 103.77)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(singapore, 11f)

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    markerDataList: List<GeometriesItem> = emptyList(),
    content: @Composable () -> Unit = {}
) {
    val singaporeState = rememberMarkerState(position = singapore)
    val singapore2State = rememberMarkerState(position = singapore2)
    val singapore3State = rememberMarkerState(position = singapore3)
    var circleCenter by remember { mutableStateOf(singapore) }
    if (singaporeState.dragState == DragState.END) {
        circleCenter = singaporeState.position
    }

    val uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val ticker by remember { mutableStateOf(0) }
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
            // Drawing on the map is accomplished with a child-based API
//            val markerClick: (Marker) -> Boolean = {
//                Log.d(TAG, "${it.title} was clicked")
//                cameraPositionState.projection?.let { projection ->
//                    Log.d(TAG, "The current projection is: $projection")
//                }
//                false
//            }
            MarkerInfoWindowContent(
                state = singapore2State,
                title = "Marker with custom info window.\nZoom in has been tapped $ticker times.",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
            ) {
                Text(it.title ?: "Title", color = Color.Blue)
            }
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