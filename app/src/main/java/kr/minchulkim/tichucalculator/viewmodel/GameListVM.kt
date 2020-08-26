package kr.minchulkim.tichucalculator.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.minchulkim.tichucalculator.domain.usecase.*
import kr.minchulkim.tichucalculator.entity.Game

@ExperimentalCoroutinesApi
class GameListVM @ViewModelInject constructor(
    private val clearGamesUseCase: ClearGamesUseCase,
    private val deleteGameUseCase: DeleteGameUseCase,
    private val loadGamesUseCase: LoadGamesUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val gameList: LiveData<List<Game>> = liveData {
        loadGamesUseCase.run(Unit).collect {
            emit(it.sortedByDescending { it.id })
        }
    }

    fun onClickClear() {
        viewModelScope.launch {
            clearGamesUseCase.run(Unit)
        }
    }

    fun onDeleteGame(game: Game) {
        viewModelScope.launch {
            deleteGameUseCase.run(game.id)
        }
    }
}