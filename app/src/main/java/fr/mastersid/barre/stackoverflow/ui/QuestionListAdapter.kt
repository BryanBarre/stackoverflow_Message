package fr.mastersid.barre.stackoverflow.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import fr.mastersid.barre.stackoverflow.data.Question
import fr.mastersid.barre.stackoverflow.QuestionItemViewHolder
import fr.mastersid.barre.stackoverflow.R

/**
 *Created by Bryan BARRE on 17/09/2021.
 */
class QuestionListAdapter(val listener:(Question)->Unit): ListAdapter<Question, QuestionItemViewHolder>(Question.DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            QuestionItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_question, parent , false)
        return QuestionItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: QuestionItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        Log.d("Adapter", "$position")
        holder.answerCount.text = "${item.answerCount}"

        holder.button.setOnClickListener{
            Log.d("ee","eeeee")
            this.listener.invoke(item)
        }
    }



}