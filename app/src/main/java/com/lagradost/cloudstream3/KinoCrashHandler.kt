package com.lagradost.cloudstream3

import android.content.Context
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class KinoCrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val sw = StringWriter()
            throwable.printStackTrace(PrintWriter(sw))
            val stackTrace = sw.toString()
            val file = File(context.getExternalFilesDir(null), "kino_crash.txt")
            file.writeText("CRASH ON THREAD: ${thread.name}\n\n$stackTrace")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        defaultHandler?.uncaughtException(thread, throwable)
    }
}
