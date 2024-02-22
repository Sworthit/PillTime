package com.example.pilltime.feature.pilldetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pilltime.compose.datepicker.PillDatePickerDialog
import com.example.pilltime.compose.dosagePicker.DosagePicker
import com.example.pilltime.compose.dosagePicker.PillTimePicker
import com.example.pilltime.compose.pillDayPicker.DayBoxContent

@Composable
fun PillDetailsScreen(
    navController: NavController,
    viewModel: PillDetailsViewModel
) {
    val pillDetails = viewModel.pill.observeAsState().value

    var pillId by rememberSaveable { mutableStateOf(0L) }
    var pillName by rememberSaveable { mutableStateOf("") }
    var pillDosage by rememberSaveable { mutableStateOf(1) }
    var selectedDays by remember { mutableStateOf(viewModel.daysOfWeek) }
    var showPillDatePicker by remember { mutableStateOf(false) }
    var pillEndDate by rememberSaveable { mutableStateOf(0L) }
    var pillDosageTimeList by remember { mutableStateOf(mutableListOf<String>()) }
    val context = LocalContext.current

    if (pillDetails != null) {
        viewModel.checkSelectedDays(pillDetails.daysOfTheWeek)
        var pillChanges = pillDetails
        pillId = pillChanges.id
        pillName = pillChanges.name
        pillDosage = pillChanges.dailyDosage
        pillEndDate = pillChanges.endDate
        selectedDays = viewModel.daysOfWeek
        pillDosageTimeList = pillChanges.dailyDosageTimes

        Scaffold(
            topBar = {
                FloatingActionButton(onClick = {
                    navController.navigate("pills")
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pill Name",
                    style = MaterialTheme.typography.bodyLarge
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = pillName,
                    onValueChange = { pillName = it },
                    placeholder = {
                        Text(
                            text = pillDetails.name
                        )
                    },
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Pill Daily Dosage",
                    style = MaterialTheme.typography.bodyLarge
                )


                DosagePicker(
                    min = 1,
                    max = 3,
                    default = pillChanges.dailyDosage,
                    onReduceTimeList = {
                        if (pillDosageTimeList.isNotEmpty()) {
                            pillDosageTimeList.removeLast()
                        }
                    },
                    onValueChange = { value ->
                        pillDosage = value
                    }
                )

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    for (i in 1..pillDosage) {
                    PillTimePicker(
                        timeSelected = if (i > pillChanges.dailyDosageTimes.size) ""
                                else pillChanges.dailyDosageTimes[i-1],
                        onTimeSelected = { pillDosageTimeList.add(it) }
                    )
                    }
                }


                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Pill Occurrence",
                    style = MaterialTheme.typography.bodyLarge
                )

            DayBoxContent(data = viewModel.daysOfWeek, onClickListener = { day ->
                viewModel.updateSelectedDaysOfWeek(day)
            })

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Last Pill Date",
                    style = MaterialTheme.typography.bodyLarge
                )

                Box() {
                    Button(onClick = { showPillDatePicker = true }) {
                        Text(
                            text = viewModel.convertMillisToDate(pillDetails.endDate),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                if (showPillDatePicker) {
                    PillDatePickerDialog(
                        onDateSelected = { pillEndDate = it },
                        onDismiss = {
                            showPillDatePicker = false
                        }
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .height(56.dp),
                        onClick = {
                            viewModel.updatePill(
                                name = pillName,
                                dailyDosage = pillDosage,
                                dailyDosageTimes = pillDosageTimeList,
                                daysOfTheWeek = viewModel.getSelectedDaysOfWeek(),
                                endDate = pillEndDate,
                                context = context
                            )
                            navController.navigate("pills")
                        }) {
                        Text(
                            text = "Update Pill",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.padding(20.dp))

                    Button(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ,
                        onClick = {
                            viewModel.deletePill(pillDetails)
                            navController.navigate("pills")
                        }) {
                        Text(
                            text = "Delete Pill",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }
            }
        }
    }
}