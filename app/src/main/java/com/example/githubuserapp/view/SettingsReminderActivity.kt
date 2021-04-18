package com.example.githubuserapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivitySettingsReminderBinding
import com.example.githubuserapp.model.Reminder
import com.example.githubuserapp.preferences.ReminderPreference
import com.example.githubuserapp.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class SettingsReminderActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener,
    View.OnClickListener {

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private var binding: ActivitySettingsReminderBinding? = null
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsReminderBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val reminderPref = ReminderPreference(this)
        binding?.toggleButton?.isChecked = reminderPref.getReminder().reminderToggle
        binding?.btnRepeatTime?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()

        binding?.toggleButton?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val repeatTime = binding?.tvRepeatTime?.text.toString()
                val repeatTitle = binding?.edtMessageAlarm?.text.toString()
                saveReminder(true)
                alarmReceiver.setAlarmRepeater(
                    this,
                    "Repeating Alarm",
                    repeatTime,
                    repeatTitle
                )

            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.reminderToggle = state
        reminderPreference.setReminder(reminder)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatTime?.text = dateFormat.format(calendar.time)
            else -> {
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_repeat_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}
