package kr.minchulkim.tichucounter.domain.usecase

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kr.minchulkim.tichucounter.data.GameRepoImpl
import kr.minchulkim.tichucounter.data.room.AppDatabase
import kr.minchulkim.tichucounter.entity.Game
import kotlin.system.measureTimeMillis

class LoadGamesUseCaseTest : TestCase() {
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
        val loadGamesUseCase = LoadGamesUseCase(gameRepository = GameRepoImpl(database.gameDao()))
        val costTimeMillis = measureTimeMillis {
            val observer = loadGamesUseCase.run(Unit).test(this)

            observer.assertNoValues()

            val game = Game.create(70)
            withContext(Dispatchers.Default) {
                database.gameDao().insertGame(game)
            }
            async { delay(100L) }.await()
            observer.assertLast {
                it.size == 1
            }
            withContext(Dispatchers.Default) {
                database.gameDao().insertGame(game)
            }
            async { delay(100L) }.await()
            observer.assertLast {
                it.size == 2
            }
            withContext(Dispatchers.Default) {
                database.gameDao().deleteAll()
            }
            async { delay(100L) }.await()
            observer.assertLast {
                it.size == 0
            }
            observer.finish()
        }
        println("costTimeMillis: $costTimeMillis") // Prints "costTimeMillis: 10017"
    }

    fun <T> Flow<T>.test(scope: CoroutineScope): TestObserver<T> {
        return TestObserver(scope, this)
    }

    class TestObserver<T>(
        scope: CoroutineScope,
        flow: Flow<T>
    ) {
        private val values = mutableListOf<T>()
        private val job: Job = scope.launch {
            flow.collect { values.add(it) }
        }

        fun assertNoValues(): TestObserver<T> {
            assertEquals(emptyList<T>(), this.values)
            return this
        }

        fun assertValues(vararg values: T): TestObserver<T> {
            assertEquals(values.toList(), this.values)
            return this
        }

        fun assertLast(predicate: (T) -> Boolean): TestObserver<T> {
            assertEquals(predicate(values.last()), true)
            return this
        }

        fun finish() {
            job.cancel()
        }
    }
}