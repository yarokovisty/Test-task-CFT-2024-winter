package com.example.testtask.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.databinding.ItemInfoBinding

class InfoAdapter() : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {
    private val info = mutableListOf<InfoModel>()

    class InfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInfoBinding.bind(view)

        fun bind(infoModel: InfoModel) {
            binding.tvTitleInfo.text = infoModel.title
            binding.tvInfo.text = infoModel.value

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return InfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(info[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addInfo(listInfo: InfoModel) {
        info.add(listInfo)
        notifyDataSetChanged()
    }
}