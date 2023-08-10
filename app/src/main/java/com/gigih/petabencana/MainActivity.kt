package com.gigih.petabencana

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gigih.petabencana.components.FilterChipsRow
import com.gigih.petabencana.components.SearchMenu
import com.gigih.petabencana.data.BencanaTable
import com.gigih.petabencana.data.UiState
import com.gigih.petabencana.ui.ViewModelFactory
import com.gigih.petabencana.ui.home.DIsasterViewModel
import com.gigih.petabencana.ui.home.DisasterList
import com.gigih.petabencana.ui.home.GoogleMapView
import com.gigih.petabencana.ui.home.defaultCameraPosition
import com.gigih.petabencana.ui.settings.Settings
import com.gigih.petabencana.ui.settings.SettingsViewModel
import com.gigih.petabencana.ui.theme.PetaBencanaTheme
import com.gigih.petabencana.utils.DataStoreUtil
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    private val themeViewModel: SettingsViewModel by viewModels()
    private lateinit var dataStoreUtil: DataStoreUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreUtil = DataStoreUtil(applicationContext)

        val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> { true }
            Configuration.UI_MODE_NIGHT_NO -> { false }
            else -> { false }
        }
        setContent {
            val theme = dataStoreUtil.getTheme(systemTheme).collectAsState(initial = systemTheme)

            PetaBencanaTheme(darkTheme = theme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            DisasterApp(
                                navController = navController
                            )
                        }
                        composable("setting") {
                            Settings(
                                dataStoreUtil = dataStoreUtil,
                                themeViewModel = themeViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DisasterApp(
    viewModel: DIsasterViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navController: NavController = rememberNavController()
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

    // Add a mutable state for the user's search query
    val searchQuery = remember { mutableStateOf("") }

    // Add a mutable state for the selected filters
    val selectedFilters = remember { mutableStateListOf<String>() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    when (disasterResponseState) {
        is UiState.Loading -> {
            // Show loading state
            // e.g., CircularProgressIndicator
            viewModel.fetchDisasterData()
            Log.d("DisasterScreen1", viewModel.fetchDisasterData().toString())
        }
        is UiState.Success -> {
            val bencanaResponse = (disasterResponseState as UiState.Success<List<BencanaTable>>).data
            // Show success state
            Log.d("DisasterScreen2", "Success: $bencanaResponse")

            // Combine the filtering logic for both filters
            val filteredBencanaList = if (searchQuery.value.isBlank() && selectedFilters.isEmpty()) {
                bencanaResponse
            } else {
                bencanaResponse.filter { bencana ->
                    // Check if the disaster type contains the search query (case-insensitive)
                    val matchesSearchQuery = bencana.instanceRegionCode?.contains(searchQuery.value, ignoreCase = true) ?: false

                    // Check if the disaster type is in the selected filters
                    val matchesFilterChips = selectedFilters.isEmpty() || selectedFilters.contains(bencana.type)

                    matchesSearchQuery && matchesFilterChips
                }
            }

            BottomSheetScaffold(
                sheetContent = { DisasterList(
                    filteredBencanaList
                ) { coordinates ->
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(coordinates, 15f)
                }
                },
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
                        markerDataList = filteredBencanaList
                    )

                    SearchMenu(
                        onQueryChange = { query->
                            searchQuery.value = query
                            Log.d("testtt", searchQuery.value)
                        },
                        onSearchSubmit = {
                            // Hide the keyboard after the user presses Enter
                            keyboardController?.hide()
                            // Clear focus to dismiss the focus from the TextField
                            focusManager.clearFocus()
                            Log.d("Testtt", viewModel.getDisasterByLocation(searchQuery.value).toString())
                        },
                        navController = navController
                    )

                    // Add FilterChips for different disasters
                    FilterChipsRow(selectedFilters) { selectedFilter ->
                        if (selectedFilters.contains(selectedFilter)) {
                            selectedFilters.remove(selectedFilter)
                        } else {
                            selectedFilters.add(selectedFilter)
                        }
                    }

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