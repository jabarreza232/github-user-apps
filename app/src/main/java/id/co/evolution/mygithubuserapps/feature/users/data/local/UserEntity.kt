package id.co.evolution.mygithubuserapps.feature.users.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val name: String? = null,
    val bio: String? = null,
    val company: String? = null,
    val location: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val publicRepos: Int? = null
)