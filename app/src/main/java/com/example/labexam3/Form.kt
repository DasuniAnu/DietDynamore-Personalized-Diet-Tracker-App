package com.example.labexam3

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Form : AppCompatActivity() {
    private lateinit var timeInput: TextView
    private lateinit var setButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)
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
        val mealNameEditText = findViewById<EditText>(R.id.MealName)
        val mealDateEditText = findViewById<EditText>(R.id.mealdate)
        timeInput = findViewById(R.id.mealtime)
        setButton = findViewById(R.id.setAlarm)
        val mealDescriptionEditText = findViewById<EditText>(R.id.mealdescription)
        val addButton = findViewById<Button>(R.id.buttonSaveMeal)

        setButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                // Update the TextView with the selected time
                timeInput.text = String.format("%02d:%02d", selectedHour, selectedMinute)

                // Set the alarm for the selected time
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                calendar.set(Calendar.SECOND, 0)

                setAlarm(calendar.timeInMillis)

            }, hour, minute, true)

            timePickerDialog.show()
            }




        addButton.setOnClickListener {
            val mealName = mealNameEditText.text.toString()
            val mealDate = mealDateEditText.text.toString()
            val mealTime = timeInput.text.toString()
            val mealDescription = mealDescriptionEditText.text.toString()

            // Logging the input values
            Log.d("Form", "Meal Name: $mealName, Date: $mealDate, Time: $mealTime, Description: $mealDescription")

            val sharedPreferences = getSharedPreferences("dietPrefs", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("dietPrefs", null)
            val type = object : TypeToken<MutableList<DietTask>>() {}.type
            val dietList: MutableList<DietTask> = if (json != null) gson.fromJson(json, type) else mutableListOf()

            dietList.add(DietTask(mealName, mealDate, mealTime, mealDescription))

            val updatedJson = gson.toJson(dietList)
            val editor = sharedPreferences.edit()
            editor.putString("dietPrefs", updatedJson)
            editor.apply()

            // Logging to confirm data has been saved
            Log.d("Form", "Updated JSON saved to SharedPreferences: $updatedJson")

            finish()
            val button3 = findViewById<Button>(R.id.buttonSaveMeal)
            button3.setOnClickListener {
                val intent1 = Intent(this, Home::class.java)
                startActivity(intent1)
            }
        }


    }


    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)  // Intent to trigger alarm receiver

        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check Android version for exact alarm scheduling
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {  // Android 12+
            // Handle exact alarm permission for Android 12+
            if (alarmManager.canScheduleExactAlarms()) {
                // Schedule the exact alarm
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
                Toast.makeText(this, "Alarm set for: " + timeInput.text, Toast.LENGTH_SHORT).show()
            } else {
                // Redirect user to settings to enable exact alarm permission
                Toast.makeText(this, "Please enable exact alarms in the settings", Toast.LENGTH_LONG).show()
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)

            }
        } else {
            // Schedule the alarm for devices below Android 12 (no permission required)
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
            Toast.makeText(this, "Alarm set for: " + timeInput.text, Toast.LENGTH_SHORT).show()
           }
        }

}
