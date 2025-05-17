package com.example.examlastiinlast.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun update(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun delete(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun getWordById(id: Int): Word? {
        return wordDao.getWordById(id)
    }
}