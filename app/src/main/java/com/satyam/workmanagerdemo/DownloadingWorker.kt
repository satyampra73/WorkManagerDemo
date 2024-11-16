package com.satyam.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date

class DownloadingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try {

            for (i in 0 until 3000) {
                Log.i("MyTag", "Downloading : $i")
            }

            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Log.i("MyTag","Completed $currentDate")
            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }

    }
}