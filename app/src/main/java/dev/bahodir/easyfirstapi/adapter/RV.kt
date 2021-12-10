package dev.bahodir.easyfirstapi.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.bahodir.easyfirstapi.EasyAPI
import dev.bahodir.easyfirstapi.databinding.RvItemBinding
import java.io.File

class RV() : ListAdapter<EasyAPI, RV.VH>(DU()) {

    inner class VH(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(easyAPI: EasyAPI) {
            binding.login.text = easyAPI.login
            Picasso.get().load(easyAPI.avatar_url).into(binding.avatarUrl)
        }
    }

    class DU : DiffUtil.ItemCallback<EasyAPI>() {
        override fun areItemsTheSame(oldItem: EasyAPI, newItem: EasyAPI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EasyAPI, newItem: EasyAPI): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }
}