package com.satyam.workmanagerdemo

import android.os.Bundle
import android.text.style.UpdateLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView
    companion object{
        const val KEY_COUNT_VALUE = "key_count"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<AppCompatButton>(R.id.btnStart)
        textView = findViewById(R.id.textView)
        btnStart.setOnClickListener{
            setPeriodicWorkRequest()
        }

    }

    private fun setOneTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)

        val data :Data = Data.Builder().putInt(KEY_COUNT_VALUE,125).build()
        val constraints = Constraints.Builder().setRequiresCharging(false).build()
        val uploadRequest =OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val compressingRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val downloadingWorker = OneTimeWorkRequest.Builder(DownloadingWorker::class.java).build()
        val parallelWorks = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(downloadingWorker)
        parallelWorks.add(filteringRequest)

        workManager
            .beginWith(parallelWorks)
            .then(compressingRequest)
            .then(uploadRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this , Observer{
            textView.text = it.state.toString()
            if(it.state.isFinished){
                val data = it.outputData
                val message = data.getString(UploadWorker.KeyWorker)
                Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest = androidx.work.PeriodicWorkRequest.Builder(DownloadingWorker::class.java,16,
            TimeUnit.MINUTES).build()

        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)


    }

}