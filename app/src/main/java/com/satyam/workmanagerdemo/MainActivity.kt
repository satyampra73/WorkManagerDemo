package com.satyam.workmanagerdemo

import android.os.Bundle
import android.text.style.UpdateLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl

class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<AppCompatButton>(R.id.btnStart)
        textView = findViewById(R.id.textView)
        btnStart.setOnClickListener{
            setOneTimeWorkRequest()
        }

    }

    private fun setOneTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)
        val uploadRequest =OneTimeWorkRequest.Builder(UploadWorker::class.java).build()

        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this , Observer{
            textView.text = it.state.toString()
        })
    }

}