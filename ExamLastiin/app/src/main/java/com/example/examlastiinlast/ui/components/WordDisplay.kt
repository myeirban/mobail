package com.example.examlastiinlast.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.examlastiinlast.data.Word

@Composable
fun WordDisplay(
    word: Word?,
    showMongolian: Boolean,
    showForeign: Boolean,
    onMongolianClick: () -> Unit,
    onForeignClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMongolianText by remember { mutableStateOf(showMongolian) }
    var showForeignText by remember { mutableStateOf(showForeign) }

    LaunchedEffect(showMongolian) {
        showMongolianText = showMongolian
    }

    LaunchedEffect(showForeign) {
        showForeignText = showForeign
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        word?.let { currentWord ->
            if (showMongolianText) {
                Text(
                    text = currentWord.mongolian,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMongolianClick() }
                        .padding(16.dp)
                )
            }

            if (showForeignText) {
                Text(
                    text = currentWord.foreign,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onForeignClick() }
                        .padding(16.dp)
                )
            }
        } ?: run {
            Text(
                text = "No words available",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 