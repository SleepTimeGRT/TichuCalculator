package kr.minchulkim.tichucalculator.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @ColumnInfo(name = "a_trick_point")
    val aTrickPoint: Int = 0,
    @ColumnInfo(name = "a_tichu_point")
    val aTichuPoint: Int = 0,
    @ColumnInfo(name = "b_trick_point")
    val bTrickPoint: Int = 0,
    @ColumnInfo(name = "b_tichu_point")
    val bTichuPoint: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0


    val aPoint:Int get() = aTrickPoint + aTichuPoint
    val bPoint:Int get() = bTrickPoint + bTichuPoint

    companion object {
        fun create(aTrickPoint: Int, aTichuPoint: Int = 0, bTichuPoint: Int = 0): Game {
            return Game(
                aTrickPoint = aTrickPoint,
                aTichuPoint = aTichuPoint,
                bTrickPoint = 100 - aTrickPoint,
                bTichuPoint = bTichuPoint
            )
        }

        fun createWinGame(aWin: Boolean, aTichuPoint: Int = 0, bTichuPoint: Int = 0): Game {
            return Game(
                aTrickPoint = if (aWin) 200 else 0,
                aTichuPoint = aTichuPoint,
                bTrickPoint = if (aWin) 0 else 200,
                bTichuPoint = bTichuPoint
            )
        }
    }
}