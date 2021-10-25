package fr.mastersid.barre.stackoverflow.ui

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import fr.mastersid.barre.stackoverflow.backend.ListQuestionJson
import fr.mastersid.barre.stackoverflow.backend.QuestionJson
import fr.mastersid.barre.stackoverflow.data.Question

/**
 *Created by Bryan BARRE on 24/09/2021.
 */
class QuestionMoshiAdapter {
    @FromJson
    fun fromJson(listQuestionJson: ListQuestionJson):List<Question>{
        return listQuestionJson.items.map {
                questionJson -> Question(questionJson.title , questionJson.answer_count)
        }
    }

    @ToJson
    fun toJson(listQuestion: List <Question >): ListQuestionJson {
        return ListQuestionJson(
            listQuestion.map {
                    question -> QuestionJson(question.title , question.answerCount)
            }
        )
    }
}