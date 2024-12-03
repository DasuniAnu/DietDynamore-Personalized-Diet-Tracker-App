package com.example.labexam3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : AppCompatActivity() {
    private lateinit var adapter:Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
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
        // Initialize RecyclerView and Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager (this)

        // Initialize the adapter and set it to RecyclerView
        adapter = Adapter(this, mutableListOf())
        recyclerView.adapter = adapter

        // Load data
        adapter.reloadData()

        var button2= findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val intent1 = Intent(this, Form::class.java)
            startActivity(intent1)
        }
        //timer-----------
        val openTimerButton: Button = findViewById(R.id.openTimerButton)
        openTimerButton.setOnClickListener {
            val intent = Intent(this, timer::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        //reload data when returning to this activity
        adapter.reloadData()
    }




}