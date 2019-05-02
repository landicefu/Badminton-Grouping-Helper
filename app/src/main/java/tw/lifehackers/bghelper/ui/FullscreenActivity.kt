package tw.lifehackers.bghelper.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.ui.matches.MatchesFragment
import tw.lifehackers.bghelper.ui.players.PlayerListFragment
import tw.lifehackers.bghelper.util.finishApp

class FullscreenActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val APP_FINISH_THRESHOLD = 800L
    }

    private var lastBackPressTimeStamp = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        initUI()
    }

    private fun initUI() {
        matchesButton.setOnClickListener(this)
        editPlayersButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        goFullScreen()
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount != 0) {
            super.onBackPressed()
            return
        }

        val currentTimeStamp = System.currentTimeMillis()
        if (currentTimeStamp - lastBackPressTimeStamp < APP_FINISH_THRESHOLD) {
            finishApp()
        } else {
            lastBackPressTimeStamp = currentTimeStamp
            Toast.makeText(this, "Press back again to close app", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goFullScreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.matchesButton -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, MatchesFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            R.id.editPlayersButton -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, PlayerListFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }
}
