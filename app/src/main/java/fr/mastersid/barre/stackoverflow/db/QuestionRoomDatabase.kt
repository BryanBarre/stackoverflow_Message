package fr.mastersid.barre.stackoverflow.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mastersid.barre.stackoverflow.data.Question
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 *Created by Bryan BARRE on 27/09/2021.
 */

@Dao
interface QuestionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertAll(list:List<Question>)


    @Query("Select* FROM question_table")
    fun getQuestionList(): Flow<List<Question>>
}

@Database(
    entities = [Question::class],
    version=1,
    exportSchema = false
)


abstract class QuestionRoomDatabase:RoomDatabase(){
    abstract fun questionDao():QuestionDao
}

@InstallIn(SingletonComponent::class)
@Module
class QuestionRoomDatabaseModule{
    @Provides
    @Singleton
    fun provideQuestionDao(questionRoomDatabase: QuestionRoomDatabase):QuestionDao{
        return questionRoomDatabase.questionDao()
    }

    @Provides
    @Singleton
    fun provideQuestionRoomDatabase(@ApplicationContext appContext:Context):QuestionRoomDatabase{
        return Room.databaseBuilder(
            appContext.applicationContext,
            QuestionRoomDatabase::class.java,
            "question-database"
        ).build()
    }
}