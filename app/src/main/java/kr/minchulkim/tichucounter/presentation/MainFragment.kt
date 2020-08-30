package kr.minchulkim.tichucounter.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kr.minchulkim.tichucounter.R
import kr.minchulkim.tichucounter.databinding.MainFragmentBinding
import kr.minchulkim.tichucounter.viewmodel.GameListVM

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