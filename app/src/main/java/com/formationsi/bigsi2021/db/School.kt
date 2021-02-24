package com.formationsi.bigsi2021.db

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Entity(tableName = "tableschool")
data class School (
    @PrimaryKey
    @Json(name = "price")  var name_school:String,
    @Json(name = "id") var name_director:String,
    @Json(name = "img_src") var num_phone:String
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

        suspend fun getFromMosh(): List<School> {
               return MoshiRetrofitApi.retrofitService.getProperties()
        }
         fun getGoogleSheetData(): ArrayList<MutableMap<String, String>>? {
            return GoogleSheet().getDataSheet().value
        }

    }