package com.example.workmanagernotification

import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.work.Data
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.workmanagernotification.custom.TimePickerCustom
import com.example.workmanagernotification.work.NotifyWork
import com.example.workmanagernotification.work.NotifyWork.Companion.NOTIFICATION_ID
import com.example.workmanagernotification.work.NotifyWork.Companion.NOTIFICATION_WORK
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault
import java.util.concurrent.TimeUnit.MILLISECONDS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        userInterface()
    }

    private fun userInterface() {

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var collapsing_toolbar_l = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_l)
        var done_fab = findViewById<FloatingActionButton>(R.id.done_fab)
        var date_p = findViewById<DatePicker>(R.id.date_p)
        var time_p = findViewById<TimePickerCustom>(R.id.time_p)
        var coordinator_l = findViewById<CoordinatorLayout>(R.id.coordinator_l)

        setSupportActionBar(toolbar)

        val titleNotification = getString(R.string.notification_title)
        collapsing_toolbar_l.title = titleNotification

        done_fab.setOnClickListener {
            val customCalendar = Calendar.getInstance()
            customCalendar.set(
                date_p.year, date_p.month, date_p.dayOfMonth, time_p.hour, time_p.minute, 0
            )
            val customTime = customCalendar.timeInMillis
            val currentTime = currentTimeMillis()
            if (customTime > currentTime) {
                val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
                val delay = customTime - currentTime
                scheduleNotification(delay, data)

                val titleNotificationSchedule = getString(R.string.notification_schedule_title)
                val patternNotificationSchedule = getString(R.string.notification_schedule_pattern)
                make(
                    coordinator_l,
                    titleNotificationSchedule + SimpleDateFormat(
                        patternNotificationSchedule, getDefault()
                    ).format(customCalendar.time).toString(),
                    LENGTH_LONG
                ).show()
            } else {
                val errorNotificationSchedule = getString(R.string.notification_schedule_error)
                make(coordinator_l, errorNotificationSchedule, LENGTH_LONG).show()
            }
        }
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, REPLACE, notificationWork).enqueue()
    }
}
