package kr.minchulkim.tichucalculator.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
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
import timber.log.Timber
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    @Inject
    lateinit var loadGameUseCase: LoadGamesUseCase

    @Inject
    lateinit var gameRepository: GameRepository

    private lateinit var binding: MainFragmentBinding
    private val adapter: GameAdapter = GameAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        setupViews()
//        CoroutineScope(Dispatchers.Main).launch {
//            gameRepository.getGameListFlow()
//                .onStart {
//                    Timber.d("onStart getGameListFlow")
//                }
//                .onEach {
//                    Timber.d("onEach getGameListFlow")
//                }
//                .onCompletion {
//                    Timber.d("onCompletion getGameListFlow")
//                }
//                .catch {e->
//                    Timber.e(e)
//                }
//                .asLiveData()
//                .observe(viewLifecycleOwner, Observer {
//                    adapter.submitList(it)
//                    binding.aPointSumText.text = it.sumBy { it.aPoint }.toString()
//                    binding.bPointSumText.text = it.sumBy { it.bPoint }.toString()
//                })
//        }
        gameRepository.getGameListObservable().observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.submitList(it)
                binding.aPointSumText.text = it.sumBy { it.aPoint }.toString()
                binding.bPointSumText.text = it.sumBy { it.bPoint }.toString()
            }
    }

    private fun setupViews() {
        binding.aTeamText.setText(R.string.team_a)
        binding.bTeamText.setText(R.string.team_b)
        binding.gameRecyclerView.adapter = adapter
    }

}