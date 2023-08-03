package sso.hyeon.caffeinbody

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sso.hyeon.caffeinbody.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private val binding: FragmentSettingBinding by lazy {
        FragmentSettingBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.switch1.isChecked = App.prefs.isAlarmTrue!!
        binding.switch1.setOnCheckedChangeListener{_, ischecked->
            if (ischecked){
                App.prefs.isAlarmTrue = true
            }else{
                App.prefs.isAlarmTrue = false
            }
        }



        binding.textView4.setOnClickListener {
            startActivity(
                Intent(activity, Survey1Activity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        binding.textView5.setOnClickListener {
            startActivity(
                Intent(activity, HeartrateCalculation::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //   initRecycler() }
        return binding.root
    }



}