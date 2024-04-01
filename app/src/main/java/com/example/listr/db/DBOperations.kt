package com.example.listr.db

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val TAG = "DB Operations"

class DBOperations : ViewModel() {
    private val db = Firebase.firestore
    private var _collectionName = MutableStateFlow<String?>(null)
    val collectionName: StateFlow<String?>
        get() = _collectionName

    fun setCollectionName(name: String) {
        _collectionName.value = name
    }

    fun addCharacter(fullName : String) {
        val user = hashMapOf(
            "name" to fullName,
        )

        collectionName.value?.let {
            db.collection(it)
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }

    fun getCharacters(): StateFlow<List<Character>> {
        val listFlow = MutableStateFlow(emptyList<Character>())

        collectionName.value?.let { collectionName ->
            db.collection(collectionName)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.documents.isNotEmpty()) {
                        listFlow.value = snapshot.documents.map { doc ->
                            Character(doc["name"] as String)
                        }
                    } else {
                        Log.d(TAG, "Current data: null")
                    }
                }
        }
        return listFlow
    }


}