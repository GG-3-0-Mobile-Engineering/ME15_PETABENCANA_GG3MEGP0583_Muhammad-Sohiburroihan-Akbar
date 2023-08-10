package com.gigih.petabencana.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gigih.petabencana.ui.theme.PetaBencanaTheme

@Composable
fun SearchMenu(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(50),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    Modifier
                        .fillMaxHeight()
                        .clip(shape = RoundedCornerShape(50))
                        .background(Color.Green)
                        .clickable {
                            navController.navigate("setting")
                        },
                    tint = Color.White,
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(text = stringResource(com.gigih.petabencana.R.string.cari_disini))
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    // Call the onSearchSubmit callback when the user presses Enter
                    onSearchSubmit()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Set the IME action to Done
            )
        )
        onQueryChange(query)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipsRow(
    selectedFilters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val filters = listOf("flood", "earthquake", "volcano") // Add more disasters if needed
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                selected = selectedFilters.contains(filter),
                onClick = { onFilterSelected(filter) },
                label = { Text(text = filter) },
                colors = if (selectedFilters.contains(filter)) {
                    FilterChipDefaults.filterChipColors(containerColor = Color.Green)
                } else {
                    FilterChipDefaults.filterChipColors(containerColor = Color.White)
                },
                border = FilterChipDefaults.filterChipBorder(borderColor = Color.Transparent)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetaBencanaTheme {
    }
}