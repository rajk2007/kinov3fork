package com.rajk2007.kino.ui

import android.content.Intent
import android.os.Bundle
import com.lagradost.cloudstream3.MainActivity

class SplashActivity : androidx.appcompat.app.AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
