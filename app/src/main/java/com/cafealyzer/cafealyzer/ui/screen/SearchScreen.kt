package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.remote.response.FindCafeResult
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(mapsViewModel: MapsViewModel, onCafeSelected: (FindCafeResult) -> Unit) {
    Column {
        var searchText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = { Text("Cari Cafe Anda atau Kompetitor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    mapsViewModel.findCafe(searchText)
                }
            )
        )
        mapsViewModel.findCafeData.observeAsState().value?.let { cafeResults ->
            if (cafeResults.isNotEmpty()) {
                CafeSearchResults(cafeResults = cafeResults) { selectedCafe ->
                    onCafeSelected(selectedCafe)
                }
            }
        }
    }
}

@Composable
fun CafeSearchResults(cafeResults: List<FindCafeResult>, onCafeSelected: (FindCafeResult) -> Unit) {
    LazyColumn {
        items(cafeResults) { cafe ->
            Text(
                text = cafe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCafeSelected(cafe) }
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}