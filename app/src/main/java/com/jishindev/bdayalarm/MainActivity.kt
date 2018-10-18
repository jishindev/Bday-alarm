package com.jishindev.bdayalarm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var dateTime = TIME_TO_SURPRISE
        set(value) {
            field = value
            tvDateTime?.text = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        createNotificationChannel()
        setSurprise(dateTime)

        tvDateTime?.text = TIME_TO_SURPRISE
        tvDateTime?.setOnClickListener {
            setDateTime()
        }
       /* btnSetSurprise?.setOnClickListener {
            setSurprise(dateTime)
        }*/
    }

    private fun setDateTime() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            Log.d(this@MainActivity::class.java.simpleName, "setDateTime: date set: $dayOfMonth:$month:$year")

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                Log.d(this@MainActivity::class.java.simpleName, "setDateTime: time set: $hourOfDay:$minute")

                dateTime = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.US).format(calendar.time)
                setSurprise(dateTime)

            }, 12, 0, false).show()

        }, 2018, 6, 6).show()
    }
}
