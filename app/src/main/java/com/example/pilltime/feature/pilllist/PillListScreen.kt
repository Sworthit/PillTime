package com.example.pilltime.feature.pilllist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pilltime.compose.pillcard.PillCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PillListScreen(
    factory: PillListViewModelFactory,
    navController: NavController,
    viewModel: PillListViewModel = viewModel(factory = factory),
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("add_pill") },
            ) {
                Text(text = "New Pill")
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(
                items = viewModel.state.pills,
                itemContent = { it ->
                    PillCard(pillEntity = it, selectedPill = {
                        navController.navigate("pill_details/${it}")
                    })
                }
            )
        }
    }
}

