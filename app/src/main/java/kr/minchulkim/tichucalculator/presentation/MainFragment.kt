package kr.minchulkim.tichucalculator.presentation

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kr.minchulkim.tichucalculator.R
import kr.minchulkim.tichucalculator.databinding.MainFragmentBinding
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.domain.usecase.AddGameUseCase
import kr.minchulkim.tichucalculator.domain.usecase.LoadGamesUseCase
import kr.minchulkim.tichucalculator.entity.Game
import kr.minchulkim.tichucalculator.viewmodel.GameListVM
import kr.minchulkim.tichucalculator.viewmodel.PointEditorVM
import timber.log.Timber
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val gameListVM: GameListVM by activityViewModels()

    private lateinit var binding: MainFragmentBinding
    private val adapter: GameAdapter = GameAdapter { game ->
        MaterialAlertDialogBuilder(requireContext()).setMessage(R.string.delete_game_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                gameListVM.onDeleteGame(game)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                //no-op
            }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        setupViews()
        observeVM()
    }

    private fun observeVM() {
        gameListVM.gameList.observe(viewLifecycleOwner, {
            val scrollState = binding.gameRecyclerView.layoutManager?.onSaveInstanceState()
            adapter.submitList(it) {
                scrollState?.let {
                    binding.gameRecyclerView.layoutManager?.onRestoreInstanceState(scrollState)
                }
            }
            binding.aPointSumText.text = it.sumBy { it.aPoint }.toString()
            binding.bPointSumText.text = it.sumBy { it.bPoint }.toString()
        })
    }

    private fun setupViews() {
        binding.aTeamText.setText(R.string.team_a)
        binding.bTeamText.setText(R.string.team_b)
        binding.gameRecyclerView.adapter = adapter
    }

}