package sso.hyeon.caffeinbody.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import sso.hyeon.caffeinbody.R
import sso.hyeon.caffeinbody.databinding.FragmentSurvey2Binding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Survey1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Survey2Fragment  : Fragment() {

    lateinit var navController : NavController
    private val binding: FragmentSurvey2Binding by lazy {
        FragmentSurvey2Binding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //   initRecycler() }

        navController = Navigation.findNavController(requireView())

        binding.buttonNext.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_questionFragment)
        }
        return binding.root
    }



}