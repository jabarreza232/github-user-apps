package id.co.evolution.mygithubuserapps.feature.users.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun observeUsers(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users WHERE username LIKE '%' || :query || '%'")
    suspend fun searchUsersLocal(query: String): List<UserEntity>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnoreUsers(users: List<UserEntity>)

    @Query("UPDATE users SET username = :username, avatarUrl = :avatarUrl WHERE id = :id")
    suspend fun updateBasicUserInfo(id: Int, username: String, avatarUrl: String)

    @Transaction
    suspend fun upsertBasicUsers(users: List<UserEntity>) {
        insertIgnoreUsers(users)
        users.forEach { user ->
            updateBasicUserInfo(user.id, user.username, user.avatarUrl)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(user: UserEntity)
}