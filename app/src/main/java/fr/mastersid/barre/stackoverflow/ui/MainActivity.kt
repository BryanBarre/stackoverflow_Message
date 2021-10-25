package fr.mastersid.barre.stackoverflow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import fr.mastersid.barre.stackoverflow.databinding.ActivityMainBinding

import fr.mastersid.barre.stackoverflow.databinding.FragmentQuestionBinding
import fr.mastersid.barre.stackoverflow.databinding.FragmentQuestionListBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity () {
    override fun onCreate ( savedInstanceState : Bundle ?) {
        super . onCreate ( savedInstanceState )
        val binding = ActivityMainBinding.inflate ( layoutInflater )

        setContentView ( binding . root )
    }
}