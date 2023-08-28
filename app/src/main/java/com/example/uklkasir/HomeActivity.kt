package com.example.uklkasir

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.uklkasir.adapter.ItemAdapter
import com.example.uklkasir.database.CafeDatabase
import com.example.uklkasir.database.Menu

class HomeActivity : AppCompatActivity() {
    lateinit var recyclerMakanan: RecyclerView
    lateinit var recyclerMinuman: RecyclerView
    lateinit var recyclerSnack: RecyclerView

    lateinit var adapterMakanan: ItemAdapter
    lateinit var adapterMinuman: ItemAdapter
    lateinit var adapterSnack: ItemAdapter

    lateinit var db: com.example.uklkasir.database.CafeDatabase
    lateinit var addButton: ImageButton
    lateinit var mejaButton: ImageButton
    lateinit var userButton: ImageButton
    lateinit var transaksiButton: ImageButton
    lateinit var checkoutButton: Button

    private var listMakanan = mutableListOf<com.example.uklkasir.database.Menu>()
    private var listMinuman = mutableListOf<com.example.uklkasir.database.Menu>()
    private var listSnack = mutableListOf<com.example.uklkasir.database.Menu>()

    private var listCart = arrayListOf<Int?>()

    var nama: String = ""
    var role: String = ""
    var id_user: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerMakanan = findViewById(R.id.recyclerMakanan)
        recyclerMinuman = findViewById(R.id.recyclerMinuman)
        recyclerSnack = findViewById(R.id.recyclerSnack)
        addButton = findViewById(R.id.buttonAdd)
        mejaButton = findViewById(R.id.buttonMeja)
        transaksiButton = findViewById(R.id.buttonTransaksi)
        userButton = findViewById(R.id.buttonUser)
        checkoutButton = findViewById(R.id.checkOut)

        db = com.example.uklkasir.database.CafeDatabase.getInstance(applicationContext)
        adapterMakanan = ItemAdapter(listMakanan)
        adapterMinuman = ItemAdapter(listMinuman)
        adapterSnack = ItemAdapter(listSnack)

        recyclerMakanan.adapter = adapterMakanan
        recyclerMakanan.layoutManager = LinearLayoutManager(this)
        recyclerMinuman.adapter = adapterMinuman
        recyclerMinuman.layoutManager = LinearLayoutManager(this)
        recyclerSnack.adapter = adapterSnack
        recyclerSnack.layoutManager = LinearLayoutManager(this)

        swipeToGesture(recyclerMakanan)

        nama = intent.getStringExtra("name")!!
        role = intent.getStringExtra("role")!!
        id_user = intent.getIntExtra("id_user", 0)
        Toast.makeText(applicationContext, "Logged in as " + nama, Toast.LENGTH_SHORT).show()

        if(role != "Admin"){
            addButton.isEnabled = false
            addButton.visibility = View.INVISIBLE
        }
        if(listCart.size == 0){
            checkoutButton.isEnabled = false
            checkoutButton.visibility = View.INVISIBLE
        }

        addButton.setOnClickListener{
            val moveIntent = Intent(this@HomeActivity, AddItemActivity::class.java)
            startActivity(moveIntent)
        }
        mejaButton.setOnClickListener{
            val moveIntent = Intent(this@HomeActivity, ListMejaActivity::class.java)
            startActivity(moveIntent)
        }
        userButton.setOnClickListener{
            val moveIntent = Intent(this@HomeActivity, ListUserActivity::class.java)
            startActivity(moveIntent)
        }
        transaksiButton.setOnClickListener{

        }
        checkoutButton.setOnClickListener{
            val moveIntent = Intent(this@HomeActivity, CartActivity::class.java)
            moveIntent.putIntegerArrayListExtra("CART", listCart)
            moveIntent.putExtra("id_user", id_user)
            startActivity(moveIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getMenu()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getMenu(){
        listMakanan.clear()
        listMinuman.clear()
        listSnack.clear()
        listMakanan.addAll(db.cafeDao().getMenuFilterJenis("Food"))
        listMinuman.addAll(db.cafeDao().getMenuFilterJenis("Drink"))
        listSnack.addAll(db.cafeDao().getMenuFilterJenis("Snack"))
        adapterMakanan.notifyDataSetChanged()
        adapterMinuman.notifyDataSetChanged()
        adapterSnack.notifyDataSetChanged()
    }

    private fun swipeToGesture(itemRv: RecyclerView){
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try{
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            var adapter: ItemAdapter = itemRv.adapter as ItemAdapter
                            db.cafeDao().deleteMenu(adapter.getItem(position))
                            adapter.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            val moveIntent = Intent(this@HomeActivity, EditItemActivity::class.java)
                            var adapter: ItemAdapter = itemRv.adapter as ItemAdapter
                            var menu = adapter.getItem(position)
                            moveIntent.putExtra("ID", menu.id_menu)
                            startActivity(moveIntent)
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}