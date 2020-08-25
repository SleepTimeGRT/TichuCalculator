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
import org.junit.Test
import kotlin.system.measureTimeMillis

class AddGameUseCaseTest : TestCase() {

    lateinit var database: AppDatabase

    public override fun setUp() {
        super.setUp()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }

    public override fun tearDown() {
        database.close()
    }

    @Test
    fun testBuildUseCaseFlowable() = runBlocking {
        val addGameUseCase = AddGameUseCase(gameRepository = GameRepoImpl(database.gameDao()))
        val costTimeMillis = measureTimeMillis {
            val params = Game.create(70 )
            addGameUseCase.run(params)
            val list = withContext(Dispatchers.Default) {
                database.gameDao().loadAllGames().first()
            }
            Assert.assertEquals(list.first(), params)
        }
        println("costTimeMillis: $costTimeMillis") // Prints "costTimeMillis: 10017"
    }
}