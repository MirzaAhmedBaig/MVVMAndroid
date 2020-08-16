package com.mab.mvvmandroid.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mab.mvvmandroid.data.db.entities.CURRENT_USER_ID
import com.mab.mvvmandroid.data.db.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun upsert(user: User)

    @Query("select * from User where uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}
