package kr.minchulkim.tichucalculator.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @ColumnInfo(name = "a_large_tichu")
    val aLargeTichu: TichuResult = TichuResult.NONE,
    @ColumnInfo(name = "a_small_tichu")
    val aSmallTichu: TichuResult = TichuResult.NONE,
    @ColumnInfo(name = "a_one_two")
    val aOneTwo: Boolean = false,
    @ColumnInfo(name = "a_point")
    val aPoint: Int = 0,
    @ColumnInfo(name = "b_large_tichu")
    val bLargeTichu: TichuResult = TichuResult.NONE,
    @ColumnInfo(name = "b_small_tichu")
    val bSmallTichu: TichuResult = TichuResult.NONE,
    @ColumnInfo(name = "b_one_two")
    val bOneTwo: Boolean = false,
    @ColumnInfo(name = "b_point")
    val bPoint: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun create(winnerIsA: Boolean, winnerPoint: Int): Game {
            return if (winnerIsA) {
                Game(
                    aPoint = winnerPoint,
                    bPoint = 100 - winnerPoint
                )
            } else {
                Game(
                    aPoint = 100 - winnerPoint,
                    bPoint = winnerPoint
                )
            }
        }
    }
}