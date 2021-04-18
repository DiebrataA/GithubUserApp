package com.example.githubuserapp.preferences

import android.content.Context
import com.example.githubuserapp.model.Reminder

class ReminderPreference(context: Context) {
    companion object{
        const val PREFERENCE_NAME = "preference_name"
        private const val REMINDER = "reminderToggle"
    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder){
        val edit = preference.edit()
        edit.putBoolean(REMINDER, value.reminderToggle)
        edit.apply()
    }

    fun getReminder(): Reminder{
        val model = Reminder()
        model.reminderToggle = preference.getBoolean(REMINDER, false)
        return model
    }
}