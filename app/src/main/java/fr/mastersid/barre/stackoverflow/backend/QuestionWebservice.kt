package fr.mastersid.barre.stackoverflow.backend

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.mastersid.barre.stackoverflow.data.Question
import fr.mastersid.barre.stackoverflow.ui.QuestionMoshiAdapter
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/**
 *Created by Bryan BARRE on 24/09/2021.
 */

private const val BASE_URL = "https://api.stackexchange.com/2.3/"


interface QuestionWebservice {
    @GET("questions?pagesize=20&order=desc&sort=activity&site=stackoverflow")
    //    @GET("questions?pagesize=20&order=desc&sort=activity&site=stackoverflow")
    suspend fun getQuestionList(
        //@Query("cnt")count:Int=20,//.........................................
        @Query("last_activity_date")last_activity:Int,
    ): List<Question>
}



@Module
@InstallIn(SingletonComponent :: class)
object QuestionWebserviceModule {

    @Provides
    @Singleton
    fun provideMoshi (): Moshi {
        return Moshi.Builder ()
            .add(KotlinJsonAdapterFactory ())
            .add(QuestionMoshiAdapter ())
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit (moshi: Moshi): Retrofit {
        return Retrofit.Builder ()
            .addConverterFactory(MoshiConverterFactory.create (moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideQuestionWebservice(retrofit: Retrofit): QuestionWebservice {
        return retrofit.create(QuestionWebservice :: class.java)
    }
}


data class ListQuestionJson(
    val items: List<QuestionJson>
    )

data class QuestionJson(
    val title:String,
    val answer_count:Int
    )

