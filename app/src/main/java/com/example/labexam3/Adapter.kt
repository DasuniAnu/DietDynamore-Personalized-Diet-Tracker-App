package com.example.labexam3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class Adapter(

    private val context: Context,

    private var dietList: MutableList<DietTask>,

) : RecyclerView.Adapter<Adapter.DietTaskViewHolder>() {

    // ViewHolder for a task item view
     class DietTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDate: TextView = itemView.findViewById(R.id.date)
        val taskTime: TextView = itemView.findViewById(R.id.taskTime)
        val taskDescription: TextView = itemView.findViewById(R.id.mealDescription)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    // Inflates the layout and creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return DietTaskViewHolder(view)
    }

    // Binds the data to the views in the ViewHolder
    override fun onBindViewHolder(holder: DietTaskViewHolder, position: Int) {
        val dietTasks = dietList[position]
        holder.taskName.text = dietTasks.taskName
        holder.taskDate.text = dietTasks.date
        holder.taskTime.text = dietTasks.taskTime
        holder.taskDescription.text = dietTasks.taskDescription


        holder.deleteButton.setOnClickListener {
            removediet(position)
        }

        holder.editButton.setOnClickListener {
          updatediet(position)
       }
        holder.itemView.setOnClickListener{}
    }

    override fun getItemCount(): Int {
        return dietList.size
    }

    fun reloadData() {
        val sharedPreferences = context.getSharedPreferences("dietPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("dietPrefs", null)
        val type = object : TypeToken<MutableList<DietTask>>() {}.type
        dietList= if (json != null) gson.fromJson(json, type) else mutableListOf()
        notifyDataSetChanged()
    }

    private fun removediet(position: Int) {
        dietList.removeAt(position)
        notifyItemRemoved(position)

        val sharedPreferences = context.getSharedPreferences("dietPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val updatedJson = gson.toJson( dietList)
        val editor = sharedPreferences.edit()
        editor.putString("dietList", updatedJson)
        editor.apply()

        notifyItemRangeChanged(position,  dietList.size)
    }
  private fun updatediet(position: Int) {
      val dietTasks = dietList[position]
      val intent = Intent(context, edit::class.java)
      intent.putExtra("position", position)
      intent.putExtra("taskName", dietTasks.taskName)
      intent.putExtra("date", dietTasks.date)
      intent.putExtra("taskTime", dietTasks.taskTime)
      intent.putExtra("taskDescription", dietTasks.taskDescription)
      context.startActivity(intent)

  }

}
