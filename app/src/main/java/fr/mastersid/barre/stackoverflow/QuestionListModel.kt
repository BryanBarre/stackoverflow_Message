package fr.mastersid.barre.stackoverflow

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mastersid.barre.stackoverflow.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Bryan BARRE on 17/09/2021.
 */
@HiltViewModel
class QuestionListModel @Inject constructor(
    state : SavedStateHandle,

    private val repository : QuestionRepository
    ) : ViewModel() {

    //new
    private val _requestState=MutableLiveData(QuestionRepository.RequestState.NONE_OR_DONE)
    val requestState: MutableLiveData<QuestionRepository.RequestState>
    get()= _requestState
    val questionList=repository.questionList.asLiveData()
//new
    init {
    viewModelScope.launch(Dispatchers.IO) {
        repository.requestState.collect { state ->
            _requestState.postValue(state)
        }
    }
}

    fun updateList () {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuestionList(last_activity = DEFAULT_LAST)
        }
    }

    companion object {
        const val DEFAULT_LAST=1

        //val NO_QUESTION = emptyList<Question>()
        //private const val STATE_KEY_RESULT = "state_key_result"
    }
}