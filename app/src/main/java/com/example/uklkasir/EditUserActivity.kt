package com.example.uklkasir

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.uklkasir.database.CafeDatabase

class EditUserActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var buttonSave: Button
    lateinit var pilihRole: Spinner

    lateinit var db: CafeDatabase

    var id: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        editName = findViewById(R.id.username)
        editEmail = findViewById(R.id.Email)
        editPassword = findViewById(R.id.password)
        buttonSave = findViewById(R.id.btnConfirm)
        pilihRole = findViewById(R.id.SpinnerRole)

        id = intent.getIntExtra("ID", 0)

        db = CafeDatabase.getInstance(applicationContext)

        init()
        setDataSpinner()

        buttonSave.setOnClickListener{
            if(editName.text.toString().isNotEmpty() && editEmail.text.toString().isNotEmpty() && editPassword.text.toString().isNotEmpty()){
                db.cafeDao().updateUser(editName.text.toString(), editEmail.text.toString(), editPassword.text.toString(),
                    pilihRole.selectedItem.toString(), id)
                finish()
            }
        }
    }
    fun init(){
        editName = findViewById(R.id.username)
        editEmail = findViewById(R.id.Email)
        pilihRole = findViewById(R.id.SpinnerRole)
        buttonSave = findViewById(R.id.btnConfirm)
    }
    fun setDataSpinner(){
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.roles










            , android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pilihRole.adapter = adapter
    }
}