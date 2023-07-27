package com.gigih.petabencana

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigih.petabencana.components.SearchMenu
import com.gigih.petabencana.data.BencanaResponse
import com.gigih.petabencana.data.GeometriesItem
import com.gigih.petabencana.data.UiState
import com.gigih.petabencana.ui.ViewModelFactory
import com.gigih.petabencana.ui.home.DIsasterViewModel
import com.gigih.petabencana.ui.home.DisasterList
import com.gigih.petabencana.ui.home.GoogleMapView
import com.gigih.petabencana.ui.home.defaultCameraPosition
import com.gigih.petabencana.ui.theme.PetaBencanaTheme
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetaBencanaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisasterApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisasterApp(
    viewModel: DIsasterViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }
    val disasterResponseState by viewModel.disasterResponse.collectAsState(initial = UiState.Loading)
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetShape = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
    )

    when (disasterResponseState) {
        is UiState.Loading -> {
            // Show loading state
            // e.g., CircularProgressIndicator
            viewModel.fetchDisasterData()
            Log.d("DisasterScreen1", viewModel.fetchDisasterData().toString())
        }
        is UiState.Success -> {
            val bencanaResponse = (disasterResponseState as UiState.Success<BencanaResponse>).data
            // Show success state
            Log.d("DisasterScreen2", "Success: $bencanaResponse")

            BottomSheetScaffold(
                sheetContent = { DisasterList(
                    (bencanaResponse.result.objects?.output?.geometries ?: emptyList()) as List<GeometriesItem>
                ) },
                scaffoldState = scaffoldState,
                sheetShape = bottomSheetShape,
                sheetPeekHeight = 300.dp
            ) {
                Box(Modifier.fillMaxSize()) {
                    GoogleMapView(
                        modifier = Modifier.matchParentSize(),
                        cameraPositionState = cameraPositionState,
                        onMapLoaded = {
                            isMapLoaded = true
                        },
                        markerDataList = (bencanaResponse.result.objects?.output?.geometries ?: emptyList()) as List<GeometriesItem>
                    )
                    SearchMenu()
                    if (!isMapLoaded) {
                        AnimatedVisibility(
                            modifier = Modifier
                                .matchParentSize(),
                            visible = !isMapLoaded,
                            enter = EnterTransition.None,
                            exit = fadeOut()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .wrapContentSize()
                            )
                        }
                    }


                }
            }
        }
        is UiState.Error -> {
            val errorMessage = (disasterResponseState as UiState.Error).errorMessage
            // Show error state
            // e.g., Show error message
            Log.d("DisasterScreen3", "Error: $errorMessage")
            Text(text = "Error: $errorMessage")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetaBencanaTheme {
    }
}