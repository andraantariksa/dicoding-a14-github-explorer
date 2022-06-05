package id.shaderboi.github.data.data_source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.shaderboi.github.domain.model.UserBrief

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userBrief: UserBrief
) {
    constructor(userBrief: UserBrief) : this(0, userBrief)
}