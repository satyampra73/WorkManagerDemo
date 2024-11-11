package com.satyam.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object{
        const val KeyWorker = "key_worker"
    }
    override fun doWork(): Result {
        try {
            val count = inputData.getInt(MainActivity.KEY_COUNT_VALUE,0)
            for (i in 0 until count) {
                Log.i("MyTag", "Uploading : $i")
            }

            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            val outPutData = Data.Builder().putString(KeyWorker,currentDate).build()
            return Result.success(outPutData)

        } catch (e: Exception) {
            return Result.failure()
        }

    }
}