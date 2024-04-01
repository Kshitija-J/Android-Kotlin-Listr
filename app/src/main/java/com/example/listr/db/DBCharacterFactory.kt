package com.example.listr.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DBCharacterFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBOperations::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DBOperations() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}