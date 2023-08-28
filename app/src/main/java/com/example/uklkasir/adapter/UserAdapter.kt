package com.example.uklkasir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uklkasir.R
import com.example.uklkasir.database.Menu
import com.example.uklkasir.database.User

class UserAdapter(var items: List<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    lateinit var Username: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return UserAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.txtUsername.text = items[position].nama
        holder.txtEmail.text = items[position].email
        holder.txtRole.text = items[position].role
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public fun getItem(position: Int): User {
        return items.get(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtUsername: TextView
        var txtEmail: TextView
        var txtRole: TextView

        init {
            txtUsername = view.findViewById(R.id.namaUser)
            txtEmail = view.findViewById(R.id.emailUser)
            txtRole = view.findViewById(R.id.roleUser)

        }
    }
}

