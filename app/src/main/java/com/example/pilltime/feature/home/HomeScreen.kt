package com.example.pilltime.feature.home

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.pilltime.R
import com.example.pilltime.compose.pillcard.PillCard
import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.ui.theme.Pink2
import java.time.LocalDate

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state

    var filteredPills: List<PillEntity> by remember { mutableStateOf(emptyList()) }
    val date = LocalDate.now()
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    filteredPills = state.pills.filter { pill ->
        pill.daysOfTheWeek.contains(date.dayOfWeek.name.lowercase())
    }

    Column {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Pink2
            ),
            shape = RoundedCornerShape(corner = CornerSize(25.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "${state.greetings} ${state.userName}", fontSize = 26.sp)
                Text(text = "Pills for today", fontSize = 26.sp)
            }

        }

        HorizontalDivider(thickness = 1.dp)

        if (filteredPills.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                items(
                    items = filteredPills,
                    itemContent = { it ->
                        PillCard(pillEntity = it, selectedPill = {
                            navController.navigate("pill_details/${it}")
                        })
                    }
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "Nothing for today :)")
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.happy_cat, imageLoader),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }
}