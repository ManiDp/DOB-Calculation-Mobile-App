package com.example.dobcalculationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val date_picker: Button = findViewById(R.id.date_picker)

        date_picker.setOnClickListener() {
            clickDatePicker();
        }

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvInMinutes = findViewById(R.id.tvAgeInMinutes)
    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance();
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val date = myCalendar.get(Calendar.DAY_OF_MONTH)

        var dpd = DatePickerDialog(
            this,
            { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                println("selected year is $selectedYear  $selectedMonth $selectedDayOfMonth")

                var selectedStringDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                tvSelectedDate?.text = selectedStringDate

                var sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                var theDate = sdf.parse(selectedStringDate)

                theDate?.let {
                    var selectedDateInMinutes = (theDate.time) / 60000

                    var currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        var currentDateInMinutes = currentDate.time / 60000

                        var differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            date
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 84600000
        dpd.show()
    }
}