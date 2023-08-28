package com.example.uklkasir

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uklkasir.adapter.MejaAdapter
import com.example.uklkasir.adapter.UserAdapter
import com.example.uklkasir.database.CafeDatabase
import com.example.uklkasir.database.Meja
import com.example.uklkasir.database.User

class ListUserActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var addUserButton: ImageButton

    lateinit var adapter: UserAdapter
    private var listUser = mutableListOf<User>()

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)

        recycler = findViewById(R.id.recyclerUser)
        addUserButton = findViewById(R.id.buttonAddUser)

        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(listUser)
        recycler.adapter = adapter

        swipeToGesture(recycler)

        addUserButton.setOnClickListener{
            val moveIntent = Intent(this@ListUserActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getUser()
    }

    fun getUser(){
        listUser.clear()
        listUser.addAll(db.cafeDao().getAllUser())
        adapter.notifyDataSetChanged()
    }

    private fun swipeToGesture(itemRv: RecyclerView){
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try{
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            var adapter: UserAdapter = itemRv.adapter as UserAdapter
                            db.cafeDao().deleteUser(adapter.getItem(position))
                            adapter.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            val moveIntent = Intent(this@ListUserActivity, EditUserActivity::class.java)
                            var adapter: UserAdapter = itemRv.adapter as UserAdapter
                            var user = adapter.getItem(position)
                            moveIntent.putExtra("ID", user.id_user)
                            startActivity(moveIntent)
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}