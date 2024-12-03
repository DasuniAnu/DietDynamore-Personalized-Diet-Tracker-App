package com.example.labexam3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
class timer : AppCompatActivity() {
    private lateinit var timerText: TextView
    private lateinit var timeInput: EditText
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button
    private var isRunning = false
    private var timeLeftInMillis: Long = 600000 // Default 10 minutes
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_timer)


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

        timerText = findViewById(R.id.timerText)
        timeInput = findViewById(R.id.timeInput)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            if (!isRunning) {
                val input = timeInput.text.toString()

                if (input.isNotEmpty()) {
                    val timeInMinutes = input.toLongOrNull()

                    if (timeInMinutes != null) {
                        // Convert user input to milliseconds
                        timeLeftInMillis = timeInMinutes * 60000
                        startTimer()
                    } else {
                        Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Enter a time in minutes", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Pause Button Logic
        pauseButton.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            }
        }

        // Reset Button Logic
        resetButton.setOnClickListener {
            resetTimer()
        }

        // Update the Timer TextView initially
        updateTimerText()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                isRunning = false
                startButton.text = "Start"
            }
        }.start()

        isRunning = true
        startButton.text = "Running"
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isRunning = false
        startButton.text = "Resume"
    }

    private fun resetTimer() {
        timeLeftInMillis = 600000 // Reset to default 10 minutes
        updateTimerText()
        startButton.text = "Start"
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        timerText.text = timeFormatted
    }
}