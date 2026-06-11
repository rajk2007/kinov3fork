package com.rajk2007.kino.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lagradost.cloudstream3.MainActivity
import com.lagradost.cloudstream3.plugins.RepositoryManager
import com.lagradost.cloudstream3.ui.settings.extensions.RepositoryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoInstallerActivity : AppCompatActivity() {

    companion object {
        val REPOS = listOf(
            "https://raw.githubusercontent.com/self-similarity/MegaRepo/builds/repo.json",
            "https://raw.githubusercontent.com/recloudstream/extensions/master/repo.json",
            "https://raw.githubusercontent.com/phisher98/cloudstream-extensions-phisher/refs/heads/builds/repo.json",
            "https://raw.githubusercontent.com/SaurabhKaperwan/CSX/builds/CS.json"
        )
        const val PREF_REPOS_INSTALLED = "kino_repos_installed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("kino_prefs", MODE_PRIVATE)
        val alreadyInstalled = prefs.getBoolean(PREF_REPOS_INSTALLED, false)

        if (!alreadyInstalled) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    REPOS.forEach { url ->
                        try {
                            val name = url.split("/").getOrNull(3) ?: "Repository"
                            RepositoryManager.addRepository(RepositoryData(name, url))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    prefs.edit().putBoolean(PREF_REPOS_INSTALLED, true).apply()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    goToMain()
                }
            }
        } else {
            goToMain()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
