package sso.hyeon.caffeinbody

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import sso.hyeon.caffeinbody.databinding.ActivityDetailBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import java.text.DecimalFormat
import java.util.*
import kotlin.math.pow


class DetailActivity : AppCompatActivity() {
    val multiply = App.prefs.multiply//평균 반감기 시간에 곱하는 값

    private val lineChartData = mutableListOf<Entry>()
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        //    supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        if (multiply != 0.0f) {
            drawGraph(multiply!!.toDouble())
        }else{
            drawGraph(1.0)
        }

        var chartWeekLine = binding.linechart
        setWeekLine(chartWeekLine)

        if (App.prefs.currentcaffeine == null){
            binding.textView11.setText("0mg")
        }else{
            binding.textView11.setText(App.prefs.currentcaffeine + "mg")
        }


    }
    companion object{
        fun calculateCaffeinLeft(volume: Float, time: Float, halfTime: Float, half: Float): Float{
            var t = time/halfTime
            var leftCaffeine = volume * (half).pow(t)//지수함수 기준 반감기 계산식
            if (leftCaffeine<1){//1 이하면 초기화
                leftCaffeine = 0f
            }

            //Log.e("detail", "$volume $time $halfTime $half $t" )
            //Log.e("leftCafffeine", leftCafffeine.toString())
            return leftCaffeine
        }

        fun getTime():Float {
            val calendar = Calendar.getInstance()
            val date = Date()
            calendar.setTime(date)
            var time = (calendar.time.hours*60 + calendar.time.minutes)/60.toFloat()

            return time
        }

        fun getDate():Int {
            var startTimeCalendar = Calendar.getInstance()
            val currentDate = startTimeCalendar.get(Calendar.DATE)
            //Log.e("detail", "todayDate: $currentDate")

            return currentDate
        }

        fun getMonth():Int {
            var startTimeCalendar = Calendar.getInstance()
            val currentMonth = startTimeCalendar.get(Calendar.MONTH) + 1
            //Log.e("detail", "todayDate: $currentMonth")

            return currentMonth
        }

        fun getYear():Int {
            var startTimeCalendar = Calendar.getInstance()
            val currentYear = startTimeCalendar.get(Calendar.YEAR)

            return currentYear
        }

        fun minusDays(): Int{//카페인 등록 날짜와 현재 날짜 차 계산
            var registered_y = App.prefs.registeredYear
            var registered_M = App.prefs.registeredMonth
            var registered_D = App.prefs.registeredDate
            // Log.e("detail: y", registered_y.toString())
            if (registered_y !== 0){
                val today = LocalDate.now()

                //Log.e("detail", "registered date:  $registered_y $registered_M $registered_D")
                val registeredDate = LocalDate.of(registered_y!!, registered_M!!, registered_D!!)
                val gap = ChronoUnit.DAYS.between(registeredDate, today)
                //Log.e("detail daysgap",gap.toString())
                return gap.toInt()
            }else{
                return 0
            }
        }
    }

    //새 카페인이 추가되면 총 마신 카페인량이 저장됨
    fun drawGraph(sensitivity: Double){
        //var caffeineVolume = App.prefs.todayCaf//초기화됨
        var remainCaf = App.prefs.remainCafTmp
        var halfTime = App.prefs.halftime
        var nowTime = getTime()

        binding.textView11.setText(App.prefs.currentcaffeine + "mg")//얘도 같이 초기화됨
        binding.textView2.setText(remainCaf.toString() + "mg")//얘도 같이 초기화됨
        if (remainCaf != 0f) {
            //Log.e("detail", "remainCaf: $remainCaf, nowdate: $nowDate, registeredDate: " + App.prefs.registeredDate)
            //var registeredT = App.prefs.registeredTime
            if (minusDays()>=0){//registered 23시, nowTime 1시
                //var leftCaffeine = calculateCaffeinLeft(remainCaf!!.toFloat(), nowTime + 24*minusDays() - registeredT!!, halfTime, 0.5f)
                //Log.e("detail nowT", "$nowTime registeredT: $registeredT")
                var i = 0
                var leftCaffeineStable = remainCaf
                Log.e("detail", "leftStabled: $leftCaffeineStable")
                while (leftCaffeineStable!! >= 1){
                    leftCaffeineStable =String.format("%.1f",(remainCaf!!/(2.0).pow(i).toFloat())).toFloat()
                    Log.e("detail", "더블 확인$leftCaffeineStable")
                    lineChartData.add(Entry((nowTime + halfTime*i++), leftCaffeineStable))
                    Log.e("detail hahah", "entry1: " + (nowTime + halfTime*i) + " entry2: " + leftCaffeineStable)
                }
                //Log.e("detail", "nowtime1")
            }else{
                Log.e("detail", "알 수 없는 에러")//7월 31일/8월 1일 7월 28일/8월4일
            }
        } else{
            lineChartData.add(Entry(0.toFloat(), 0.toFloat()))
        }


    }


    private fun setWeekLine(lineChart: LineChart) {
        val lineData = LineData()
        val set1 = LineDataSet(lineChartData, "체내 남은 카페인 양")
        set1.setDrawFilled(true)
        set1.setColor(ContextCompat.getColor(this, R.color.brown))
        set1.valueTextSize = 8f
        set1.setCircleColor(ContextCompat.getColor(this, R.color.brown))
        set1.setDrawCircleHole(false)
        set1.circleSize = 2F
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(this, R.drawable.fade_all)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = R.color.pink
        }

        lineData.addDataSet(set1)
        lineChart.setData(lineData)

        val xAxis = lineChart.xAxis
        xAxis.setDrawLabels(true)

        lineData.setValueFormatter(MyValueFormatter())
        //xAxis.labelCount = 60
        //제일 처음 마신 시간을 그래프의 가장 앞쪽에
        val xAxisVals = ArrayList<String>(Arrays.asList("0시", "1시", "2시", "3시", "4시", "5시", "6시", "7시", "8시", "9시", "10시", "11시", "12시", "13시", "14시", "15시", "16시", "17시", "18시", "19시", "20시", "21시", "22시", "23시", "다음날", "1시", "2시", "3시", "4시", "5시", "6시", "7시", "8시", "9시", "10시", "11시", "12시", "13시", "14시", "15시", "16시"))
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisVals)
        xAxis.granularity = 1f
        //xAxis.granularity = 1f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM  // x축 라벨 위치
        xAxis.setDrawLabels(true)  // Grid-line 표시
        xAxis.setDrawAxisLine(true)  // Axis-Line 표시
        //왼쪽 y축
        val yLAxis = lineChart.axisLeft
        yLAxis.axisMinimum = 0f  // y축 최소값(고정)

        // 오른쪽 y축 값
        val yRAxis = lineChart.axisRight
        yRAxis.setDrawLabels(false)
        yRAxis.setDrawAxisLine(false)
        yRAxis.setDrawGridLines(false)

        lineChart.description.isEnabled = false
        lineChart.setVisibleXRangeMaximum(35f)
        lineChart.invalidate()
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    inner class MyValueFormatter : ValueFormatter() {
        private val mFormat: DecimalFormat
        fun getFormattedValue(
            value: Float,
            entry: Map.Entry<*, *>?,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler?
        ): String {
            // write your logic here
            return mFormat.format(value).toString() + " $" // e.g. append a dollar-sign
        }

        init {
            mFormat = DecimalFormat("###,###,##0.0") // use one decimal
        }
    }
}