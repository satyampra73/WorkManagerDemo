package com.satyam.workmanagerdemo

import android.os.Bundle
import android.text.style.UpdateLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<AppCompatButton>(R.id.btnStart)
        btnStart.setOnClickListener{
            setOneTimeWorkRequest()
        }

    }

    private fun setOneTimeWorkRequest(){
        val uploadRequest =OneTimeWorkRequest.Builder(UploadWorker::class.java).build()

        WorkManager.getInstance(applicationContext).enqueue(uploadRequest)
    }

}