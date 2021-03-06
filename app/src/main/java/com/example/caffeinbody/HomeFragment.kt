
package com.example.caffeinbody

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.caffeinbody.DetailActivity.Companion.calculateCaffeinLeft
import com.example.caffeinbody.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.Entry
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import java.lang.Math.E
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    var count = 1

    // TODO: Rename and change types of parameters
    private val dataClient by lazy { Wearable.getDataClient(activity) }
    private val messageClient by lazy { Wearable.getMessageClient(getActivity()) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(getActivity()) }
    private val nodeClient by lazy { Wearable.getNodeClient(getActivity()) }
    private val clientDataViewModel by viewModels<ClientDataViewModel>()
    //getActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setUI()
        arguments?.let {

        }
    }

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //   initRecycler()
        setUI()
        Log.e("home", "onCreateView")

        binding.addBeverageBtn.setOnClickListener{
            sendFavorite()
            activity?.let{
            val selectActivity =  DrinkTypeActivity()
            val intent = Intent(context, selectActivity::class.java)
            startActivity(intent)
        }}
        binding.showDetailText.setOnClickListener{
            activity?.let{
                val selectActivity =  DetailActivity()
                val intent = Intent(context, selectActivity::class.java)
                startActivity(intent)
            }
        }
        binding.myPageBtn.setOnClickListener{
            val recommendActivity =  RecommendActivity()
            val intent = Intent(context, recommendActivity::class.java)
            startActivity(intent)
        }
        binding.checkWatchBtn.setOnClickListener{
            startWearableActivity()
            Log.e("????????? ????????? ?????? on HomeFrag", "??????!!!")
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUI()
        Log.e("home", "resume")
    }

////////////////????????? ?????? ?????? ????????? ????????? ????????? ????????? ??? ?????? ???
    private fun startWearableActivity() {
        lifecycleScope.launch {
            try {
                val nodes = nodeClient.connectedNodes.await()//?????? ??????
                Log.e("nodes: ", nodes[0].toString())//?????? ????????? ??????
                // Send a message to all nodes in parallel
                nodes.map { node ->//???????????? ????????? ??????
                    async {
                        messageClient.sendMessage(node.id, START_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

                Log.d(TAG, "Starting activity requests sent successfully")
            } catch (cancellationException: CancellationException) {
                Log.e(TAG, "??????1")
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "??????2")
            }
        }
    }
    ///////////////???????????? ????????? ?????????(????????? ?????????...?)
    private fun sendFavorite() {
        Log.e("?????????", "")
        lifecycleScope.launch {
            Log.e("TAG", "??????")
            try {
                val request = PutDataMapRequest.create(FAVORITE_PATH).apply {
                    dataMap.putString(FAVORITE_KEY, "?????? ?????????: " + (++count).toString())
                    //dataMap.putString(FAVORITE_KEY, (++count).toString())//???????????? ???????????? ?????????.
                    //dataMap.putStringArrayList(FAVORITE_KEY, ?????????)//???????????? ?????????
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()
                Log.e(TAG, "DataItem saved: $result")
            } catch (cancellationException: CancellationException) {
                Log.e(TAG, "?????????")
            } catch (exception: Exception) {
                Log.d(TAG, "Saving DataItem failed: $exception")
            }
        }
    }

    private fun setUI(){
        val todayCaf = App.prefs.todayCaf
        binding.intakenCaffeineText.setText(todayCaf.toString())
        App.prefs.dayCaffeine?.let { binding.maximumADayText.setText(it) }

        var nowTime = DetailActivity.getTime()
        var registeredTime = App.prefs.registeredTime//?????? ?????? ????????? ????????? ??????
        var halfTime =
            DetailActivity.calHalfTime(getString(R.string.basicTime).toInt(), App.prefs.multiply!!)//-> ????????? ?????? ????????? ??????
        //Log.e("home", getString(R.string.basicTime).toInt().toString())
        var leftCaffeine = calculateCaffeinLeft(todayCaf!!.toFloat(), nowTime- registeredTime!!, halfTime, 0.5f)
        putCurrentCaffeine(leftCaffeine)
        val servingsize = App.prefs.currentcaffeine//?????? ?????? ???????????? sensitivity??? currentcaffeine??? ?????? ??????

        var percent = 1.0

        if(servingsize!= null) {
            binding.AvailableCaffeineText.setText(servingsize)
            percent = servingsize!!.toDouble() / App.prefs.sensetivity!!.toDouble()
        }else{
            binding.AvailableCaffeineText.setText(App.prefs.sensetivity)
            App.prefs.currentcaffeine = App.prefs.sensetivity
        }
        binding.heart.start()
        binding.heart.waveHeightPercent = (percent)!!.toFloat()
    //  percent?.let { binding.heart.setProgress(it.toInt()) }
    }


    fun putCurrentCaffeine(caffeineLeft: Float){
        //---------------??????????????? ??????-----------------
        //TODO ??????
        var servingsize = App.prefs.sensetivity?.toDouble()//?????? ???????????? ???????????????
        Log.e("home", "caffeineLeft: $caffeineLeft, servingSize: $servingsize")
        if (servingsize != null) {
            if((servingsize - caffeineLeft!!) > 0 ) {
                var current = servingsize - caffeineLeft
                App.prefs.currentcaffeine = "%.2f".format(current)
            }
            else App.prefs.currentcaffeine = "0"
        }
        //----------------------------------------
    }



    companion object {
        private const val TAG = "HomeFragment"

        private const val START_ACTIVITY_PATH = "/start-activity3"
        private const val FAVORITE_PATH = "/favorite"
        private const val FAVORITE_KEY = "favorite"
    }

}