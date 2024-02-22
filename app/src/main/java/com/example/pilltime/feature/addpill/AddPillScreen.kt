package com.example.pilltime.feature.addpill

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pilltime.compose.datepicker.PillDatePickerDialog
import com.example.pilltime.compose.dosagePicker.DosagePicker
import com.example.pilltime.compose.dosagePicker.PillTimePicker
import com.example.pilltime.compose.pillDayPicker.DayBoxContent

@Composable
fun AddPillScreen(
    factory: AddPillViewModelFactory,
    navController: NavController,
    viewModel: AddPillViewModel = viewModel(factory = factory)
) {
    var pillName by rememberSaveable { mutableStateOf("") }
    var pillDosage by rememberSaveable { mutableStateOf(1) }
    var showPillDatePicker by remember { mutableStateOf(false) }
    var pillEndDate by rememberSaveable { mutableStateOf(0L) }
    val pillDosageTimeList by remember { mutableStateOf(viewModel.pillsDosageTimes) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            FloatingActionButton(onClick = { navController.navigate("pills") }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    ) {
        innerPadding ->
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
                        text = "Enter Pill Name"
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
                default = 1,
                onReduceTimeList = {
                    if (pillDosageTimeList.isNotEmpty()){
                        pillDosage -= 1
                        pillDosageTimeList.removeLast()
                    }
                },
                onValueChange = { value ->
                    pillDosage = value
                    if (pillDosageTimeList.size < value) {
                        pillDosageTimeList.add("")
                    }
                }
            )

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in 0 until pillDosage ) {
                    PillTimePicker(
                        onTimeSelected = {
                            if (pillDosageTimeList.isEmpty()) {
                                pillDosageTimeList.add(it)
                            } else {
                                pillDosageTimeList[i] = it
                            }
                        }
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
                        text = if (pillEndDate == 0L) "Select Date"
                        else viewModel.convertMillisToDate(pillEndDate),
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

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                onClick = {
                    viewModel.addPill(
                        context = context,
                        name = pillName,
                        dailyDosage = pillDosage,
                        endDate = pillEndDate,
                        dailyDosageTimes = pillDosageTimeList,
                        daysOfTheWeek = viewModel.getSelectedDaysOfWeek()
                    )
                    navController.navigate("pills")
                }) {
                Text(
                    text = "Add Pill",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }


}



