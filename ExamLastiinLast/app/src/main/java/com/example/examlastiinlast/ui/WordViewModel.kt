package com.example.examlastiinlast.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.examlastiinlast.data.Word
import com.example.examlastiinlast.data.WordDatabase
import com.example.examlastiinlast.data.WordRepository
import com.example.examlastiinlast.data.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository
    private val userPreferences: UserPreferences
    private val _currentWordIndex = MutableStateFlow(0)
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    private val _showMongolian = MutableStateFlow(true)
    private val _showForeign = MutableStateFlow(true)

    val currentWord: StateFlow<Word?> = combine(
        _words,
        _currentWordIndex
    ) { words, index ->
        words.getOrNull(index)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val canNavigatePrevious: StateFlow<Boolean> = _currentWordIndex.map { it > 0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val canNavigateNext: StateFlow<Boolean> = combine(_words, _currentWordIndex) { words, index ->
        index < words.size - 1
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val showMongolian: StateFlow<Boolean> = _showMongolian.asStateFlow()
    val showForeign: StateFlow<Boolean> = _showForeign.asStateFlow()

    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
        userPreferences = UserPreferences(application)

        viewModelScope.launch {
            repository.allWords.collect { words ->
                _words.value = words
            }
        }

        viewModelScope.launch {
            userPreferences.showMongolian.collect { show ->
                _showMongolian.value = show
            }
        }

        viewModelScope.launch {
            userPreferences.showForeign.collect { show ->
                _showForeign.value = show
            }
        }
    }

    fun navigateToNext() {
        if (_currentWordIndex.value < _words.value.size - 1) {
            _currentWordIndex.value++
        }
    }

    fun navigateToPrevious() {
        if (_currentWordIndex.value > 0) {
            _currentWordIndex.value--
        }
    }

    fun updateShowMongolian(show: Boolean) {
        viewModelScope.launch {
            userPreferences.updateShowMongolian(show)
        }
    }

    fun updateShowForeign(show: Boolean) {
        viewModelScope.launch {
            userPreferences.updateShowForeign(show)
        }
    }

    fun insertWord(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.update(word)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }
    
    suspend fun getWordById(id: Int): Word? {
        return repository.getWordById(id)
    }
} 