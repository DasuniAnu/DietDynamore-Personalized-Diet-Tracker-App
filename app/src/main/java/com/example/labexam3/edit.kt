package com.example.labexam3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var button6= findViewById<ImageView>(R.id.profileImage)
        button6.setOnClickListener{
            val intent1 = Intent(this, Home::class.java)
            startActivity(intent1)
        }
        var button7= findViewById<ImageView>(R.id.imageButton2)
        button7.setOnClickListener{
            val intent1 = Intent(this, Profile::class.java)
            startActivity(intent1)
        }
        val position = intent.getIntExtra("position", -1)
        val oldDate = intent.getStringExtra("taskName")
        val oldMedicineName = intent.getStringExtra("date")
        val oldDoseQuantity = intent.getStringExtra("taskTime")
        val oldTime = intent.getStringExtra("taskDescription")

        val dateInput = findViewById<EditText>(R.id.updateName)
        val medicineNameInput = findViewById<EditText>(R.id.mealdate)
        val doseQuantityInput = findViewById<EditText>(R.id.updatetime)
        val timeInput = findViewById<EditText>(R.id.updatedescription)
        val updateBtn = findViewById<Button>(R.id.updatediet)

        dateInput.setText(oldDate)
        medicineNameInput.setText(oldMedicineName)
        doseQuantityInput.setText(oldDoseQuantity)
        timeInput.setText(oldTime)

        updateBtn.setOnClickListener {
            val newDate = dateInput.text.toString()
            val newMedicineName = medicineNameInput.text.toString()
            val newDoseQuantity = doseQuantityInput.text.toString()
            val newTime = timeInput.text.toString()

            val sharedPreferences = getSharedPreferences("dietPrefs", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("dietPrefs", null)
            val type = object : TypeToken<MutableList<DietTask>>() {}.type
            val dietList: MutableList<DietTask> = if (json != null) gson.fromJson(json, type) else mutableListOf()
            if (position != -1 && position <dietList.size) {
                dietList[position] = DietTask(newDate, newMedicineName, newDoseQuantity, newTime)
            }

            val updatedJson = gson.toJson(dietList)
            val editor = sharedPreferences.edit()
            editor.putString("dietPrefs", updatedJson)
            editor.apply()

            finish()
          }

    }
}