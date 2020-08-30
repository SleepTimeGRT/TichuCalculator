package kr.minchulkim.tichucounter.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kr.minchulkim.tichucounter.R
import kr.minchulkim.tichucounter.databinding.MainActivityBinding
import kr.minchulkim.tichucounter.viewmodel.GameListVM


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var keepScreenOn: Boolean = false
    private val gameListVM: GameListVM by viewModels()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (keepScreenOn) {
            menu.findItem(R.id.keep_screen_toggle).setIcon(R.drawable.ic_baseline_highlight_24)
        } else {
            menu.findItem(R.id.keep_screen_toggle).setIcon(R.drawable.ic_outline_highlight_24)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_all) {
            showClearAlert()
            return true
        } else if (item.itemId == R.id.keep_screen_toggle) {
            toggleKeepScreenOn()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showClearAlert() {
        MaterialAlertDialogBuilder(this)
            .setMessage(R.string.delete_all_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                gameListVM.onClickClear()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                //no-op
            }
            .show()
    }

    private fun toggleKeepScreenOn() {
        val newFlag = !keepScreenOn
        if (newFlag) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            Toast.makeText(this, R.string.keep_screen_on_toast, Toast.LENGTH_SHORT).show()
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        keepScreenOn = newFlag
        invalidateOptionsMenu()
    }
}