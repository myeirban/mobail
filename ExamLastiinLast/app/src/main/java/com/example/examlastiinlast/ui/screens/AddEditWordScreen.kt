package com.example.examlastiinlast.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examlastiinlast.data.Word
import com.example.examlastiinlast.ui.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditWordScreen(
    wordId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: WordViewModel = viewModel()
) {
    var mongolianText by remember { mutableStateOf("") }
    var foreignText by remember { mutableStateOf("") }

    LaunchedEffect(wordId) {
        wordId?.let { id ->
            viewModel.getWordById(id)?.let { word ->
                mongolianText = word.mongolian
                foreignText = word.foreign
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (wordId == null) "Add Word" else "Edit Word") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val word = Word(
                                id = wordId ?: 0,
                                mongolian = mongolianText,
                                foreign = foreignText
                            )
                            if (wordId == null) {
                                viewModel.insertWord(word)
                            } else {
                                viewModel.updateWord(word)
                            }
                            onNavigateBack()
                        },
                        enabled = mongolianText.isNotBlank() && foreignText.isNotBlank()
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = mongolianText,
                onValueChange = { mongolianText = it },
                label = { Text("Mongolian Word") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = foreignText,
                onValueChange = { foreignText = it },
                label = { Text("Foreign Word") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }
} 