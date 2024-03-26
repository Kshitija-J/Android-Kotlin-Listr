package com.example.listr.nice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.listr.databinding.FragmentNiceBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

private const val TAG="NaughtyFragment"

class NiceFragment: Fragment() {
    private var _binding: FragmentNiceBinding? = null
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
        _binding = FragmentNiceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            addNiceName()
        }
    }
    private fun addNiceName(){

        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to "${binding.niceNameInput.text}",
        )

// Add a new document with a generated ID
        db.collection("nice")
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