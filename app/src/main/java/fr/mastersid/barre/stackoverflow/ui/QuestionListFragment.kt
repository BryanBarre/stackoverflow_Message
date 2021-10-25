package fr.mastersid.barre.stackoverflow.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.mastersid.barre.stackoverflow.data.Question
import fr.mastersid.barre.stackoverflow.QuestionListModel

import fr.mastersid.barre.stackoverflow.databinding.FragmentQuestionListBinding
import fr.mastersid.barre.stackoverflow.repository.QuestionRepository
import java.lang.Exception
import kotlin.math.log


/**
 *Created by Bryan BARRE on 17/09/2021.
 */
@AndroidEntryPoint
class QuestionListFragment : Fragment() {

    //-----------------------------
    private lateinit var _binding : FragmentQuestionListBinding
    //-----------------------------
    override fun onCreateView (inflater: LayoutInflater,
                               container: ViewGroup?,
                               savedInstanceState: Bundle?) : View? {
        _binding = FragmentQuestionListBinding.inflate(inflater)
        return _binding . root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val smsManager = SmsManager . getDefault ()
        val message="test reussie"

        super.onViewCreated(view, savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult (
                ActivityResultContracts . RequestPermission ()
            ) { isGranted : Boolean ->
                if ( isGranted ) {
                    smsManager . sendTextMessage ("0781597937", null , message , null , null )
                } else {
                    Snackbar . make (
                        _binding.recyclerView ,
                        "Permission needed",
                        Snackbar.LENGTH_LONG
                    ). setAction ("Go to settings") {
                        startActivity (
                            Intent (
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS ,
                                Uri.parse ("package:"+requireActivity().packageName)
                            )
                        )
                    }. show ()

// permission refus ´ee (8b)
                }
            }
        val questionListAdapter = QuestionListAdapter(
            listener = {
                when {
                    ContextCompat . checkSelfPermission (
                        requireContext () ,
                        Manifest . permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                    -> {
                        smsManager.sendTextMessage ("0781597937", null , message , null , null )
                    }
                    shouldShowRequestPermissionRationale (
                        Manifest.permission.SEND_SMS
                    ) ->  {
                    Snackbar.make (
                        _binding .root ,
                        "Permission is needed to send a message",
                        Snackbar.LENGTH_LONG
                    ).setAction ("Allow") {
                        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                    }.show ()
                        }
                    else
                    ->{
                        Log.d("zzz","eeee")
                        requestPermissionLauncher
                            .launch(Manifest.permission.SEND_SMS)
                    }
                }
            }
        )
        _binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = questionListAdapter
        }

        val questionListModel : QuestionListModel by viewModels ()

        _binding.swipeRefresh.setOnRefreshListener {
            questionListModel.updateList ()
        }

        questionListModel.questionList.observe(viewLifecycleOwner) { value ->
            questionListAdapter.submitList(value)

        }

        //new
        //fait la maj du spinner
        //en gerant de cette façon on peut gerer les erreurs reseaux

        questionListModel.requestState.observe(viewLifecycleOwner){state->
                when (state) {
                    QuestionRepository.RequestState.PENDING -> _binding.swipeRefresh.isRefreshing =
                        true
                    QuestionRepository.RequestState.NONE_OR_DONE -> _binding.swipeRefresh.isRefreshing =
                        false
                    QuestionRepository.RequestState.CRASH_IO-> {
                        _binding.swipeRefresh.isRefreshing =
                            false
                        Toast.makeText(context, "Network error", 4000).show()
                    }
                    QuestionRepository.RequestState.CRASH_HTTP-> {
                        _binding.swipeRefresh.isRefreshing =
                            false
                        Toast.makeText(context, "Request error", 4000).show()
                    }
                }


        }

    }
}