package com.formationsi.bigsi2021.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Entity(tableName = "tschool")
data class School(
    @PrimaryKey
    val nom: String = "",
    val tel: String = "",
    val ecole: String = "",
    val gresa: String = "",
    val commune: String = "",
    val fonction: String = "",
    val cycle: String = "",
    val email: String = "",
    val geo: String = ""
)

@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: School)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMutliple(s: List<School>)

    @Query("SELECT * FROM tschool WHERE nom LIKE :txtsearch OR ecole LIKE :txtsearch OR tel LIKE :txtsearch OR gresa LIKE :txtsearch")
    fun getDataQuery(txtsearch: String): LiveData<List<School>>

    @Update
    fun update(s: School)

    @Delete
    fun delete(s: School)

    @Query("DELETE FROM tschool")
    fun deleteAll()

    @Query("SELECT * FROM tschool")
    fun getAllSchool(): LiveData<List<School>>

}

@Entity(tableName = "tupdate")
data class Tupdate(
    @PrimaryKey
    val numero:String,
    val title:String
)
@Dao
interface TupdateDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: Tupdate)

    @Query("SELECT * FROM tupdate")
    fun getAlltUpdate(): LiveData<List<Tupdate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMutliple(s: List<Tupdate>)
}

@Database(entities = [School::class, Tupdate::class], version = 1)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
    abstract fun tupdateDao(): TupdateDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        fun getDatabase(context: Context): SchoolDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    "bigs_imourahi2021v20"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
