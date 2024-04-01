package com.example.listr.naughty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listr.databinding.NaughtyListItemBinding
import com.example.listr.db.Character

class NaughtyAdapters(private val naughty: List<Character>): RecyclerView.Adapter<NaughtyAdapters.NaughtyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaughtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding= NaughtyListItemBinding.inflate(inflater,parent,false)
        return NaughtyViewHolder(binding)
    }

    override fun getItemCount() = naughty.size
    override fun onBindViewHolder(holder: NaughtyViewHolder, position: Int) {
        val naughtyian = naughty[position]
        holder.bind(naughtyian)
    }
    inner class NaughtyViewHolder(private val binding: NaughtyListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(naughtyian: Character){
            binding.naughtyNameText.text=naughtyian.name
        }
    }
}