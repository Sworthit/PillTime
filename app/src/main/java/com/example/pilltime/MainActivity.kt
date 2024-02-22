package com.example.pilltime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pilltime.feature.addpill.AddPillViewModelFactory
import com.example.pilltime.feature.pilllist.PillListViewModelFactory
import com.example.pilltime.navigation.PillNavHost
import com.example.pilltime.ui.theme.PillTimeTheme
import com.example.pilltime.ui.theme.Pink4
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var addPillViewModelFactory: AddPillViewModelFactory
    @Inject
    lateinit var pillListViewModelFactory: PillListViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillTimeTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Pink4
                ) {
                    PillNavHost(
                        pillListViewModelFactory = pillListViewModelFactory,
                        addPillViewModelFactory = addPillViewModelFactory,
                    )
                }
            }
        }
    }
}