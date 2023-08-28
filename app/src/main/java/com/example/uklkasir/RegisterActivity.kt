package com.example.uklkasir

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.uklkasir.database.CafeDatabase
import com.example.uklkasir.database.User

class RegisterActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var buttonSave: Button
    lateinit var pilihRole: Spinner

    lateinit var db: com.example.uklkasir.database.CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        setDataSpinner()
        buttonSave.setOnClickListener{
            if(editName.text.toString().isNotEmpty() && editEmail.text.toString().isNotEmpty() && editPassword.text.toString().isNotEmpty()){
                db.cafeDao().insertUser(
                    com.example.uklkasir.database.User(
                        null,
                        editName.text.toString(),
                        editEmail.text.toString(),
                        editPassword.text.toString(),
                        pilihRole.selectedItem.toString()
                    )
                )
                Toast.makeText(applicationContext, "Register successful", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun init(){
        editName = findViewById(R.id.username)
        editEmail = findViewById(R.id.Email)
        editPassword = findViewById(R.id.password)
        buttonSave = findViewById(R.id.btnConfirm)
        pilihRole = findViewById(R.id.SpinnerRole)

        db = com.example.uklkasir.database.CafeDatabase.getInstance(applicationContext)
    }

    private fun setDataSpinner(){
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.roles, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pilihRole.adapter = adapter
    }
}