package com.example.listr.nice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listr.databinding.NiceListItemBinding
import com.example.listr.db.Character

class NiceAdapter(private val nice: List<Character>): RecyclerView.Adapter<NiceAdapter.NiceViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding= NiceListItemBinding.inflate(inflater,parent,false)
        return NiceViewHolder(binding)
    }

    override fun getItemCount() = nice.size
    override fun onBindViewHolder(holder: NiceViewHolder, position: Int) {
        val nician = nice[position]
        holder.bind(nician)
    }
    inner class NiceViewHolder(private val binding: NiceListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(nician: Character){
            binding.niceNameText.text=nician.name
        }
    }
}