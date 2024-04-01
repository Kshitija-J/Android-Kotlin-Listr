package com.example.listr.nice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listr.RandomDB
import com.example.listr.databinding.FragmentNiceBinding
import com.example.listr.db.DBCharacterFactory
import com.example.listr.db.DBOperations
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TAG="NiceFragment"

class NiceFragment: Fragment() {
    private var _binding: FragmentNiceBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Binding cannot be created. Is view created? "
        }
    private val db= Firebase.firestore

    private val viewModel: DBOperations by activityViewModels { DBCharacterFactory() }



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

        binding.niceRecyclerView.layoutManager = LinearLayoutManager(context)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCharacters().collect {nician ->
                    binding.niceRecyclerView.adapter = NiceAdapter(nician)
                }
            }
        }

        viewModel.setCollectionName("nice")

        binding.addButton.setOnClickListener {
            viewModel.addCharacter(binding.niceNameInput.text.toString())
        }
        binding.randomNiceButton.setOnClickListener {
            addRandomName()
        }
    }

    private fun addRandomName()
    {
        val firstname = RandomDB.firstNames.random()
        val lastname = RandomDB.lastNames.random()
        val fullname="$firstname $lastname"
        viewModel.addCharacter(fullname)
    }
    private fun getNiceName(): MutableStateFlow<List<Nician>> {

        val listFlow = MutableStateFlow(emptyList<Nician>())
        db.collection("nice")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.documents != null) {
                    listFlow.value=snapshot.documents.map { doc -> Nician(doc["name"] as String) }
                    Log.d(TAG, "Current data: ${snapshot.documents}")
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

        return listFlow
    }
    private fun addNiceName(name : String){

        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name
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