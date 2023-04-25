package com.example.mybmicalc

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mybmicalc.databinding.MyBodyItemBinding
import com.example.mybmicalc.model.body.MyBody

class MyBMICalcAdapter(
    private val listener: (MyBody) -> Unit
) : ListAdapter<MyBody, MyBMICalcAdapter.ViewHolder>(callbacks) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyBodyItemBinding.inflate(inflater, parent, false)

        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val myBody = getItem(position)
            listener(myBody)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myBody = getItem(position)
        holder.bindTo(myBody)
    }

    class ViewHolder(
        private val binding: MyBodyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(myBody: MyBody) {
            binding.heightText.text = "${myBody.height}cm / ${myBody.weight}kg"
            binding.dateText.text = DateFormat.format("yyyy/MM/dd", myBody.date)
        }
    }

    companion object {
        private val callbacks = object: DiffUtil.ItemCallback<MyBody>() {
            override fun areItemsTheSame(oldItem: MyBody, newItem: MyBody): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: MyBody, newItem: MyBody): Boolean {
                return oldItem.height == newItem.height && oldItem.created == newItem.created
            }

        }
    }
}