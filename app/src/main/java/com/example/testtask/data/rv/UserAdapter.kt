package com.example.testtask.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.data.db.User
import com.example.testtask.databinding.ItemUserBinding
import com.example.testtask.data.user_model.Result

class UserAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {
    private val users = mutableListOf<User>()

    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User) = with(binding) {
            tvName.text = String.format(
                itemView.context.getString(R.string.template_name),
                user.firstName,
                user.lastName
            )

            tvAddress.text = user.address
            tvPhoneNumber.text = user.phone
            Glide.with(itemView.context)
                .load(user.picture)
                .into(imgProfile)
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

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(listUser: List<User>) {
        users.addAll(listUser)
        notifyDataSetChanged()
    }

    fun getUser(position: Int): User {
        return users[position]
    }

    fun getSize() = users.size

    fun clearAll() = users.clear()
}