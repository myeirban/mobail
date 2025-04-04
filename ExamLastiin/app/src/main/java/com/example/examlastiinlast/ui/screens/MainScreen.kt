package com.example.examlastiinlast.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examlastiinlast.ui.WordViewModel
import com.example.examlastiinlast.ui.components.WordDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToAddWord: () -> Unit,
    onNavigateToEditWord: (Int) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: WordViewModel = viewModel()
) {
    val currentWord by viewModel.currentWord.collectAsState()
    val canNavigatePrevious by viewModel.canNavigatePrevious.collectAsState()
    val canNavigateNext by viewModel.canNavigateNext.collectAsState()
    val showMongolian by viewModel.showMongolian.collectAsState()
    val showForeign by viewModel.showForeign.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vocabulary App") },
                actions = {
                    Button( onClick = onNavigateToSettings) {
                        Text(text = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            WordDisplay(
                word = currentWord,
                showMongolian = showMongolian,
                showForeign = showForeign,
                onMongolianClick = { viewModel.updateShowMongolian(!showMongolian) },
                onForeignClick = { viewModel.updateShowForeign(!showForeign) },
                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.navigateToPrevious() },
                    enabled = canNavigatePrevious
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = { currentWord?.let { onNavigateToEditWord(it.id) } },
                    enabled = currentWord != null
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    enabled = currentWord != null
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = { viewModel.navigateToNext() },
                    enabled = canNavigateNext
                ) {
                    Text("Next")
                }
            }

            Button(
                onClick = onNavigateToAddWord,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Add New Word")
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Word") },
                text = { Text("Are you sure you want to delete this word?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            currentWord?.let { viewModel.deleteWord(it) }
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 