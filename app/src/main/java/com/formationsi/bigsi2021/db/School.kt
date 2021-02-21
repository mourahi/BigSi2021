package com.formationsi.bigsi2021.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "tableschool")
data class School (

    @PrimaryKey var name_school:String,
 var name_director:String,
 var num_phone:String
    )

@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: School)

    @Update
    fun update(s: School)

    @Delete
    fun delete(s: School)

    @Query("DELETE FROM tableschool")
    fun deleteAll()

    @Query("SELECT * FROM tableschool")
    fun getAllSchool(): LiveData<List<School>>

}

    class SchoolRepository(private val schoolDao: SchoolDao){
        val allSchool:LiveData<List<School>> = schoolDao.getAllSchool()
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(school: School) {
            schoolDao.insert(school)
        }

    }