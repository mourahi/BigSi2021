package com.formationsi.bigsi2021.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    @Update
    fun update(s: School)

    @Delete
    fun delete(s: School)

    @Query("DELETE FROM tschool")
    fun deleteAll()

    @Query("SELECT * FROM tschool")
    fun getAllSchool(): LiveData<List<School>>

}

@Database(entities = [School::class], version = 1)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        fun getDatabase(
            context: Context
        ): SchoolDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    "bigs_imourahi2021v5"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class SchoolDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.schoolDao())
                    }
                }
            }
        }

        fun populateDatabase(schoolDao: SchoolDao) {
            schoolDao.deleteAll()
            /* var s = School("عادل موراحي","","","منظومة الاعلام",)
            schoolDao.insert(s) */
        }
    }
}
