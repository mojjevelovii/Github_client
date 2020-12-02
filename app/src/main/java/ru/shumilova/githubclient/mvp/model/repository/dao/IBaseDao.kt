package ru.shumilova.githubclient.mvp.model.repository.dao

import androidx.room.*

@Dao
interface IBaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<T>)

    @Update
    fun update(vararg items: T)

    @Update
    fun update(items: List<T>)

    @Delete
    fun delete(vararg items: T)

    @Delete
    fun delete(items: List<T>)
}