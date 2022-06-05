package id.shaderboi.github.data.data_source.db.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import id.shaderboi.github.domain.model.UserBrief
import javax.inject.Inject

class UserBriefConverter {
    @Inject
    lateinit var moshi: Moshi
    private val adapter by lazy { moshi.adapter(UserBrief::class.java) }

    @TypeConverter
    fun toUserBrief(string: String): UserBrief = adapter.fromJson(string)!!

    @TypeConverter
    fun fromUserBrief(userBrief: UserBrief): String = adapter.toJson(userBrief)
}