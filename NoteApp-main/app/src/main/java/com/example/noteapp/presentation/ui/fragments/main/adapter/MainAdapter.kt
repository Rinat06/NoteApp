package com.example.noteapp.presentation.ui.fragments.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.domain.model.Note

class MainAdapter(private val onItemClick: (Note) -> Unit, private val onItemLongClick: (Note) -> Unit) : Adapter<MainAdapter.MainViewHolder>() {

    private val data = arrayListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<Note>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class MainViewHolder(private val binding: ItemNoteBinding) : ViewHolder(binding.root) {
        fun bind(model: Note) {
            with(binding){
                tvTitle.text = model.title
                tvDescription.text = model.description

                itemView.setOnClickListener {
                    onItemClick(model)
                }

                itemView.setOnLongClickListener {
                    onItemLongClick(model)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}