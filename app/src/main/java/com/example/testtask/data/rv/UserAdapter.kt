package com.example.testtask.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.databinding.ItemUserBinding
import com.example.testtask.data.user_model.Result

class UserAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UserAdapter.UserHolder>() {
    private val users = mutableListOf<Result>()

    class UserHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: Result) = with(binding) {
//            user.name.apply {
//                tvName.text = String.format(itemView.context.getString(R.string.template_name), title, first, last)
//            }
//            tvAddress.text = user.location.street.toString()
//            tvPhoneNumber.text = user.phone

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(users[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(listUser: List<Result>) {
        users.addAll(listUser)
        notifyDataSetChanged()
    }
}