package com.example.listr.nice

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.listr.databinding.FragmentNaughtyBinding
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
private const val TAG="NaughtyFragment"

class NaughtyFragment: Fragment() {
    private var _binding: FragmentNaughtyBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Binding cannot be created. Is view created? "
        }

    private val db= Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNaughtyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            addNaughtyName()
        }
    }
    private fun addNaughtyName(){

        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to "${binding.naughtyNameInput.text}",
                    )

// Add a new document with a generated ID
        db.collection("naughty")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}