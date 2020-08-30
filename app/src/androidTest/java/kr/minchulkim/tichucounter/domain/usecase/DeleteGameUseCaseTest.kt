package kr.minchulkim.tichucounter.domain.usecase

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kr.minchulkim.tichucounter.data.GameRepoImpl
import kr.minchulkim.tichucounter.data.room.AppDatabase
import kr.minchulkim.tichucounter.entity.Game
import org.junit.Assert
import kotlin.system.measureTimeMillis

class DeleteGameUseCaseTest : TestCase() {

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
        val deleteGameUseCase = DeleteGameUseCase(gameRepository = GameRepoImpl(database.gameDao()))
        val costTimeMillis = measureTimeMillis {
            val params = Game.create( 70)
            withContext(Dispatchers.Default) {
                database.gameDao().insertGame(params)
            }
            withContext(Dispatchers.Default) {
                database.gameDao().insertGame(params)
            }
            val listBeforeClear = withContext(Dispatchers.Default) {
                database.gameDao().loadAllGames().first()
            }
            Assert.assertEquals(listBeforeClear.size, 2)
            deleteGameUseCase.run(listBeforeClear.first().id)
            val listAfterClear = withContext(Dispatchers.Default) {
                database.gameDao().loadAllGames().first()
            }
            Assert.assertEquals(listAfterClear.size, 1)
            Assert.assertEquals(listAfterClear.first().id, 2)
        }
        println("costTimeMillis: $costTimeMillis") // Prints "costTimeMillis: 10017"
    }
}