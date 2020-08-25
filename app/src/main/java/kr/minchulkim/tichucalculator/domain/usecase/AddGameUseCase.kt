package kr.minchulkim.tichucalculator.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.domain.repository.GameRepository
import kr.minchulkim.tichucalculator.entity.Game
import javax.inject.Inject

class AddGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) : UseCase<Game, Unit> {

    override suspend fun run(params: Game) = gameRepository.addGame(params)

}