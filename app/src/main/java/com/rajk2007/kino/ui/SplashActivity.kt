package com.rajk2007.kino.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.ui.account.AccountSelectActivity
import com.lagradost.cloudstream3.utils.DataStore.getKey
import com.lagradost.cloudstream3.ui.setup.HAS_DONE_SETUP_KEY

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_kino)

        val root = findViewById<View>(R.id.splash_root)
        val kLetter = findViewById<TextView>(R.id.k_letter)
        val inoLetters = findViewById<TextView>(R.id.ino_letters)
        val redLine = findViewById<View>(R.id.red_line)
        val byRaj = findViewById<TextView>(R.id.by_raj)
        val cinemaRedefined = findViewById<TextView>(R.id.cinema_redefined)

        // 1. Pure black screen (0.3s) - already handled by layout background
        Handler(Looper.getMainLooper()).postDelayed({
            // 2. "K" letter slams in with spring animation
            kLetter.isVisible = true
            val kAnim = TranslateAnimation(0f, 0f, -500f, 0f).apply {
                duration = 600
                interpolator = OvershootInterpolator()
            }
            kLetter.startAnimation(kAnim)

            // 3. "INO" letters appear one by one staggered
            Handler(Looper.getMainLooper()).postDelayed({
                inoLetters.isVisible = true
                val inoAnim = AlphaAnimation(0f, 1f).apply {
                    duration = 400
                }
                inoLetters.startAnimation(inoAnim)
            }, 300)

            // 4. Thin red line expands below KINO wordmark
            Handler(Looper.getMainLooper()).postDelayed({
                redLine.isVisible = true
                redLine.scaleX = 0f
                redLine.animate().scaleX(1f).setDuration(500).setInterpolator(AccelerateDecelerateInterpolator()).start()
            }, 600)

            // 5. "by Raj Karmakar" fades in
            Handler(Looper.getMainLooper()).postDelayed({
                byRaj.isVisible = true
                byRaj.startAnimation(AlphaAnimation(0f, 1f).apply { duration = 800 })
            }, 900)

            // 6. "CINEMA. REDEFINED." appears below
            Handler(Looper.getMainLooper()).postDelayed({
                cinemaRedefined.isVisible = true
                cinemaRedefined.startAnimation(AlphaAnimation(0f, 1f).apply { duration = 800 })
            }, 1200)

            // 7. Holds for 0.5s then transitions
            Handler(Looper.getMainLooper()).postDelayed({
                val installed = getKey<Boolean>("kino_repos_installed") ?: false
                val intent = if (!installed) {
                    // Navigate to Repo Installer (Task 6 will implement this, for now use a placeholder or check HAS_DONE_SETUP_KEY)
                    Intent(this, AccountSelectActivity::class.java)
                } else {
                    Intent(this, AccountSelectActivity::class.java)
                }
                startActivity(intent)
                finish()
            }, 2500)

        }, 300)
    }
}
