package kr.minchulkim.tichucounter.data.room

import androidx.room.TypeConverter
import kr.minchulkim.tichucounter.entity.TichuResult

class Converters {
    @TypeConverter
    fun fromTichuResult(value: TichuResult?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToTichuResult(value: String?): TichuResult? {
        return value?.let { TichuResult.valueOf(it) }
    }
}