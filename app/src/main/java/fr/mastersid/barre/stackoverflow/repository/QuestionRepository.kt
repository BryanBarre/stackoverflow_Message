package fr.mastersid.barre.stackoverflow.repository

import android.util.Log
import android.widget.Toast
import fr.mastersid.barre.stackoverflow.backend.QuestionWebservice
import fr.mastersid.barre.stackoverflow.db.QuestionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

/**
 *Created by Bryan BARRE on 17/09/2021.
 */

class QuestionRepository @Inject constructor(
    private val questionWebservice: QuestionWebservice,
    private val questionDao: QuestionDao//
) {
    //new
    enum class RequestState{
        NONE_OR_DONE, PENDING, CRASH_IO, CRASH_HTTP
    }

    //new
    private val _requestState = MutableStateFlow(RequestState.NONE_OR_DONE)
    val requestState: Flow<RequestState>
    get() = _requestState
    val questionList=questionDao.getQuestionList()

    //------------------------------------------------------------------
    suspend fun updateQuestionList(last_activity: Int) {
        try {
            _requestState.emit(RequestState.PENDING)
            val listQuestion = questionWebservice.getQuestionList(
            last_activity = last_activity
            )
            questionDao.insertAll(listQuestion)
            _requestState.emit(RequestState.NONE_OR_DONE)

        }catch (e:IOException){
            Log.d("aa","l'erreur c'est IO")

            //if (){        }
            _requestState.emit(RequestState.CRASH_IO)
            Log.d("aa",RequestState.CRASH_IO.toString())

        }catch (e:HttpException){
            Log.d("aa","l'erreur c'est http")//////////////////////Lui il marche pas  !!!!!
            //if (){        }
            _requestState.emit(RequestState.CRASH_HTTP)
            Log.d("aa",RequestState.CRASH_HTTP.toString())

        }/*finally {
                _requestState.emit(RequestState.NONE_OR_DONE)
            }
        */
    }

    suspend fun sendQuestion(last_activity: Int){


    }
}
