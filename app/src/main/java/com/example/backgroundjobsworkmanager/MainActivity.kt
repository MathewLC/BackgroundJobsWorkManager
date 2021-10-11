package com.example.backgroundjobsworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var workManager= WorkManager.getInstance(this)
    private lateinit var btnStartWork: Button
    private lateinit var btnWorkStatus: Button
    private lateinit var btnResetStatus: Button
    private lateinit var btnWorkUIThread: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartWork = findViewById(R.id.start_work)
        btnWorkStatus = findViewById(R.id.work_status)
        btnResetStatus = findViewById(R.id.reset_status)
        btnWorkUIThread = findViewById(R.id.work_on_ui_thread)

        btnStartWork.setOnClickListener {
            //val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
//            val data = Data.Builder()
//                .putString("WORK_MESSAGE","Work Completed!")
//                .build()
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .build()
            val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
            1,TimeUnit.MINUTES
            ).build()
            workManager.enqueue(workRequest)
        }

        btnWorkStatus.setOnClickListener {
            val toast = Toast.makeText(this,"The work status is: ${WorkStatusSingleton.workMessage}",Toast.LENGTH_LONG)
            toast.show()
        }

        btnResetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        btnWorkUIThread.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }
    }
}