package com.cafealyzer.cafealyzer.ui.component.mapscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchPerformed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onSearchPerformed()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text("Cari Cafe Anda atau Kompetitor")
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    )
}
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearchPerformed = {}
    )
}