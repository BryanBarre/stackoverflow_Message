package fr.mastersid.barre.stackoverflow.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import java.io.Serializable

/**
 *Created by Bryan BARRE on 17/09/2021.
 */
@Entity(tableName = "question_table",primaryKeys = ["title"])//voir a changer la primary key
data class Question(val title:String, val answerCount: Int) : Serializable {

    class DiffCallback:DiffUtil.ItemCallback<Question>(){
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.title==newItem.title && oldItem.answerCount==newItem.answerCount

        }
    }
}
