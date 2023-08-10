package com.gigih.petabencana.ui.settings

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gigih.petabencana.utils.DataStoreUtil
import kotlinx.coroutines.launch

@Composable
fun Settings(
    dataStoreUtil: DataStoreUtil,
    themeViewModel: SettingsViewModel,
    navController: NavController = rememberNavController()
) {
    var switchState by remember {themeViewModel.isDarkThemeEnabled }
    val coroutineScope = rememberCoroutineScope()

    Scaffold {
        Switch(
            checked = switchState,
            onCheckedChange = {
                switchState = it

                coroutineScope.launch {
                    dataStoreUtil.saveTheme(it)
                }
            },
            thumbContent = {
                Icon(
                    modifier = Modifier
                        .size(SwitchDefaults.IconSize),
                    imageVector = if (switchState) Icons.Rounded.Build else Icons.Rounded.Settings,
                    contentDescription = "Switch Icon"
                )
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
    }
}
