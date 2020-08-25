package kr.minchulkim.tichucalculator.domain.usecase

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucalculator.data.GameRepoImpl
import kr.minchulkim.tichucalculator.data.room.AppDatabase
import kr.minchulkim.tichucalculator.entity.Game
import org.junit.Assert
import kotlin.system.measureTimeMillis

class ClearGamesUseCaseTest : TestCase() {

    lateinit var database: AppDatabase

    public override fun setUp() {
        super.setUp()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }

    public override fun tearDown() {
        database.close()
    }

    fun testTestRun() = runBlocking {
        val clearGamesUseCase = ClearGamesUseCase(gameRepository = GameRepoImpl(database.gameDao()))
        val costTimeMillis = measureTimeMillis {
            val params = Game.create( 70)
            withContext(Dispatchers.Default) {
                database.gameDao().insertGame(params)
            }
            val listBeforeClear = withContext(Dispatchers.Default) {
                database.gameDao().loadAllGames().first()
            }
            Assert.assertEquals(listBeforeClear.isEmpty(), false)
            clearGamesUseCase.run(Unit)
            val listAfterClear = withContext(Dispatchers.Default) {
                database.gameDao().loadAllGames().first()
            }
            Assert.assertEquals(listAfterClear.isEmpty(), true)
        }
        println("costTimeMillis: $costTimeMillis") // Prints "costTimeMillis: 10017"
    }
}