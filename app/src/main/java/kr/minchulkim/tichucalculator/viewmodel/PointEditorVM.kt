package kr.minchulkim.tichucalculator.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.minchulkim.tichucalculator.domain.usecase.AddGameUseCase
import kr.minchulkim.tichucalculator.entity.Game


class PointEditorVM @ViewModelInject constructor(
    private val addGameUseCase: AddGameUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _aTrickPoint: MutableLiveData<Int> = MutableLiveData()
    private val _bTrickPoint: MutableLiveData<Int> = MutableLiveData()
    private val _aTichuPoint: MutableLiveData<Int> = MutableLiveData()
    private val _bTichuPoint: MutableLiveData<Int> = MutableLiveData()
    private val _eventClearEditor: MutableLiveData<Void> = MutableLiveData()
    val eventClearEditor: LiveData<Void> = _eventClearEditor
    val aPoint: LiveData<Pair<Int, Int>>
    val bPoint: LiveData<Pair<Int, Int>>

    init {
        aPoint = MediatorLiveData<Pair<Int, Int>>().apply {
            addSource(_aTrickPoint) {
                value = (it ?: 0) to (_aTichuPoint.value ?: 0)
            }
            addSource(_aTichuPoint) {
                value = (_aTrickPoint.value ?: 0) to (it ?: 0)
            }
        }
        bPoint = MediatorLiveData<Pair<Int, Int>>().apply {
            addSource(_bTrickPoint) {
                value = (it ?: 0) to (_bTichuPoint.value ?: 0)
            }
            addSource(_bTichuPoint) {
                value = (_bTrickPoint.value ?: 0) to (it ?: 0)
            }
        }
        _aTrickPoint.value = 50
        _bTrickPoint.value = 50
    }

    fun onClickAPlusPointButton() {
        val value = _aTichuPoint.value ?: 0
        if (value < 200) {
            val newValue = value + 100
            _aTichuPoint.value = newValue
            if (newValue > 0 && (_bTichuPoint.value ?: 0) > 0) {
                _bTichuPoint.value = 0
            }
        }
    }

    fun onClickBPlusPointButton() {
        val value = _bTichuPoint.value ?: 0
        if (value < 200) {
            val newValue = value + 100
            _bTichuPoint.value = newValue
            if (newValue > 0 && (_aTichuPoint.value ?: 0) > 0) {
                _aTichuPoint.value = 0
            }
        }
    }

    fun onClickAMinusPointButton() {
        val value = _aTichuPoint.value ?: 0
        if (value > -400) {
            _aTichuPoint.value = value - 100
        }
    }

    fun onClickBMinusPointButton() {
        val value = _bTichuPoint.value ?: 0
        if (value > -400) {
            _bTichuPoint.value = value - 100
        }
    }

    fun onProgressChanged(progress: Int) {
        when (progress) {
            0 -> {
                _aTrickPoint.value = 0
                _bTrickPoint.value = 200
            }
            32 -> {
                _aTrickPoint.value = 200
                _bTrickPoint.value = 0
            }
            else -> {
                val point = -25 + (progress - 1) * 5
                _aTrickPoint.value = point
                _bTrickPoint.value = 100 - point
            }
        }
    }

    fun onClickSave() {
        viewModelScope.launch {
            val game = buildGame()
            addGameUseCase.run(game)
            _aTrickPoint.value = 50
            _bTrickPoint.value = 50
            _aTichuPoint.value = 0
            _bTichuPoint.value = 0
            _eventClearEditor.value = null
        }
    }

    private fun buildGame(): Game {
        val aTrickPoint: Int = _aTrickPoint.value ?: 0
        val bTrickPoint: Int = _bTrickPoint.value ?: 0
        val aTichuPoint: Int = _aTichuPoint.value ?: 0
        val bTichuPoint: Int = _bTichuPoint.value ?: 0
        return if (aTrickPoint == 200 || bTrickPoint == 200) {
            Game.createWinGame(
                aWin = aTrickPoint == 200,
                aTichuPoint = aTichuPoint,
                bTichuPoint = bTichuPoint
            )
        } else {
            Game.create(
                aTrickPoint = aTrickPoint,
                aTichuPoint = aTichuPoint,
                bTichuPoint = bTichuPoint
            )
        }
    }
}
