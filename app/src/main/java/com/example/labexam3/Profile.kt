package com.example.labexam3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
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
        var button8= findViewById<Button>(R.id.settings_button)
        button8.setOnClickListener{
            val intent1 = Intent(this, Login::class.java)
            startActivity(intent1)
        }
    }
}