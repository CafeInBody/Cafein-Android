package sso.hyeon.caffeinbody.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sso.hyeon.caffeinbody.databinding.FragmentSurvey1Binding
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Survey1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Survey1Fragment  : Fragment() {

    private val binding: FragmentSurvey1Binding by lazy {
        FragmentSurvey1Binding.inflate(
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

   //    val navController = Navigation.findNavController(requireView())

        binding.buttonNext.setOnClickListener {
            val detail = Survey2Fragment()


        }
        return binding.root
    }



}