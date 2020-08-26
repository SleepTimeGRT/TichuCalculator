package kr.minchulkim.tichucalculator.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kr.minchulkim.tichucalculator.R
import kr.minchulkim.tichucalculator.databinding.MainActivityBinding
import kr.minchulkim.tichucalculator.viewmodel.GameListVM

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_all) {
            MaterialAlertDialogBuilder(this)
                .setMessage(R.string.delete_all_message)
                .setPositiveButton(R.string.delete) { _, _ ->
                    gameListVM.onClickClear()
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    //no-op
                }
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}