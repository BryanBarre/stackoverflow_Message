package fr.mastersid.barre.stackoverflow

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *Created by Bryan BARRE on 17/09/2021.
 */
class QuestionItemViewHolder (view: View):RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.questionItemtitle)
    val answerCount: TextView = view.findViewById(R.id.questionItemAnswerCount)
    val button: ImageButton=view.findViewById(R.id.send_button)
}