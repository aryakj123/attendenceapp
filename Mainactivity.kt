Mainactivity.kt:

package com.example.arya

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: MutableList<Student>
    private lateinit var dateTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var classNameTextView: TextView
    private lateinit var timetableButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        studentRecyclerView = findViewById(R.id.studentRecyclerView)
        dateTextView = findViewById(R.id.dateTextView)
        saveButton = findViewById(R.id.saveButton)
        classNameTextView = findViewById(R.id.classNameTextView)
        timetableButton = findViewById(R.id.timetableButton)

        // Set Class Name
        classNameTextView.text = getString(R.string.class_name)

        // Date Initialization
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        dateTextView.text = getString(R.string.date_text, dateFormat.format(calendar.time))

        // Date Picker
        dateTextView.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                dateTextView.text = getString(R.string.date_text, dateFormat.format(calendar.time))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        // Student List
        studentList = mutableListOf(
            Student(1, "Alice Smith", AttendanceStatus.PRESENT),
            Student(2, "Bob Johnson", AttendanceStatus.PRESENT),
            Student(3, "Charlie Brown", AttendanceStatus.PRESENT),
            Student(4, "David Lee", AttendanceStatus.PRESENT),
            Student(5, "Eve Williams", AttendanceStatus.PRESENT)
        )

        // RecyclerView Setup
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(studentList)
        studentRecyclerView.adapter = studentAdapter

        // Save Button Listener
        saveButton.setOnClickListener {
            if (studentList.isNotEmpty()) {
                studentList.forEach { student ->
                    val status = when (student.attendanceStatus) {
                        AttendanceStatus.PRESENT -> "Present"
                        AttendanceStatus.ABSENT -> "Absent"
                        AttendanceStatus.LATE -> "Late"
                    }
                    println("${student.rollNumber}: ${student.name}: $status")
                }
            } else {
                println("No students found.")
            }
        }

        // Timetable Button Listener
        timetableButton.setOnClickListener {
            startActivity(Intent(this, TimetableActivity::class.java))
        }
    }
}
