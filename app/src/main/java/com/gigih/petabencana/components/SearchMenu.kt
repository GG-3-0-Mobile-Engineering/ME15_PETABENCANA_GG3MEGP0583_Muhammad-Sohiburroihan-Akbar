package com.gigih.petabencana.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigih.petabencana.ui.settings.SettingsActivity
import com.gigih.petabencana.ui.theme.PetaBencanaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMenu(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column {
        TextField(
            value = "",
            onValueChange = {},
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
                            val intent = Intent(context, SettingsActivity::class.java)
                            context.startActivity(intent)
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
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterChip(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                selected = false,
                onClick = { /*TODO*/ },
                label = { Text(text = "Banjir") },
                colors = FilterChipDefaults.filterChipColors(containerColor = Color.Green),
                border = FilterChipDefaults.filterChipBorder(borderColor = Color.Transparent)
            )
            FilterChip(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                selected = false,
                onClick = { /*TODO*/ },
                label = { Text(text = "Kabut") },
                colors = FilterChipDefaults.filterChipColors(containerColor = Color.White),
                border = FilterChipDefaults.filterChipBorder(borderColor = Color.Transparent)
            )
            FilterChip(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                selected = false,
                onClick = { /*TODO*/ },
                label = { Text(text = "Badai") },
                colors = FilterChipDefaults.filterChipColors(containerColor = Color.White),
                border = FilterChipDefaults.filterChipBorder(borderColor = Color.Transparent)
            )

//            ChipColors(
//                onClick = { /*TODO*/ },
//                colors = ChipDefaults.chipColors(backgroundColor = Color.White, contentColor = Color.Black),
//                modifier = Modifier
//                    .width(112.dp)
//                    .height(40.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.banjir),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                )
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Chip(
//                onClick = { /*TODO*/ },
//                colors = ChipDefaults.chipColors(backgroundColor = Color.White, contentColor = Color.Black),
//                modifier = Modifier
//                    .width(112.dp)
//                    .height(40.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.kabut),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                )
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Chip(
//                onClick = { /*TODO*/ },
//                colors = ChipDefaults.chipColors(backgroundColor = Color(0,136,12), contentColor = Color.White),
//                modifier = Modifier
//                    .width(112.dp)
//                    .height(40.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.badai),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                )
//            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetaBencanaTheme {
        SearchMenu()
    }
}