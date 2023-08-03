package sso.hyeon.caffeinbody

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val prefsFilename = "prefs"
    private val prefsKeyCaf = "mycaffeine"
    private val prefsKeyDrink = "mydrink"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var todayCaf: Float?//섭취한 카페인 누계
        get() = prefs.getFloat(prefsKeyCaf, 0.0f )
        set(value) = prefs.edit().putFloat(prefsKeyCaf, value!!).apply()
    var remainCaf: Float?//todayCaf 하루 지날 시 초기화 방지를 위한 카페인 누적 값(카페인 등록 이외에 변하지 않는 값)
        get() = prefs.getFloat("remainCaf", 0.0f )
        set(value) = prefs.edit().putFloat("remainCaf", value!!).apply()
    var remainCafTmp: Float?//remainCafTmp는 시간별 계산된 체내 카페인(변화되는 값)
    get() = prefs.getFloat("remainCafTmp", 0.0f )
        set(value) = prefs.edit().putFloat("remainCafTmp", value!!).apply()
    var sensetivity: String?//1회 카페인 섭취 권고량
        get() = prefs.getString("sensitivity", null)
        set(value) = prefs.edit().putString("sensitivity", value!!).apply()

    //surveydatas
    var age: String?
        get() = prefs.getString("age", null)
        set(value) = prefs.edit().putString("age", value).apply()

    var isPregnant: Boolean
        get() = prefs.getBoolean("isPregnant", false)
        set(value) = prefs.edit().putBoolean("isPregnant", value).apply()

    var heartbeat: Boolean
        get() = prefs.getBoolean("heartbeat", false)
        set(value) = prefs.edit().putBoolean("heartbeat", value).apply()

    var headache: Boolean
        get() = prefs.getBoolean("headache", false)
        set(value) = prefs.edit().putBoolean("headache", value).apply()
    //
    var currentcaffeine: String?//현재 기준 섭취 가능 카페인 양
        get() = prefs.getString("currentCaf", null)
        set(value) = prefs.edit().putString("currentCaf", value!!).apply()

    var dayCaffeine: String?//하루 카페인 섭취 권고량
        get() = prefs.getString("dayCaffeine", null)
        set(value) = prefs.edit().putString("dayCaffeine", value!!).apply()



//    var weekCafJson: ArrayList<String>//요일별 카페인 섭취 수치
//        get() {
//            val json = prefs.getString("weekCafJson", null)
//            val weekCaf = ArrayList<String>()
//            if (json != null) {
//                try {
//                    val jsonArray = JSONArray(json)
//                    for (i in 0 until jsonArray.length()) weekCaf.add(jsonArray.optString(i))
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//            return weekCaf
//        }
//        set(values) {
//            val editor = prefs.edit()
//            val jsonArray = JSONArray()
//            for (value in values) {
//                jsonArray.put(value)
//            }
//            if (values.isNotEmpty()) {
//                editor.putString("weekCafJson", jsonArray.toString())
//            } else {
//                editor.putString("weekCafJson", null)
//            }
//            editor.apply()
//        }

    var weekCafJson: String?//주별 카페인 섭취 수치
    get() = prefs.getString("weekCafJson", null)
        set(value) = prefs.edit().putString("weekCafJson", value!!).apply()

    var monthCafJson: String?//월별 카페인 섭취 수치
        get() = prefs.getString("monthCafJson", null)
        set(value) = prefs.edit().putString("monthCafJson", value!!).apply()

    var multiply: Float? //민감도 곱하는 수치
        get() = prefs.getFloat("multiply", 0.0f)
        set(value) = prefs.edit().putFloat("multiply", value!!).apply()

    var heartrateAvg: String? //심박수 평균
        get() = prefs.getString("heartrateAvg", null)
        set(value) = prefs.edit().putString("heartrateAvg", value).apply()

    var registeredDate: Int?//카페인 등록 날짜
        get() = prefs.getInt("todaydate", 0 )
        set(value) = prefs.edit().putInt("todaydate", value!!).apply()

    var registeredMonth: Int?//카페인 등록 월
        get() = prefs.getInt("registeredMonth", 0 )
        set(value) = prefs.edit().putInt("registeredMonth", value!!).apply()

    var registeredYear: Int?//카페인 등록 일
    get() = prefs.getInt("registeredYear", 0 )
        set(value) = prefs.edit().putInt("registeredYear", value!!).apply()

    var registeredTime: Float?//카페인 등록 시간
        get() = prefs.getFloat("registeredTime", 0.0f)
        set(value) = prefs.edit().putFloat("registeredTime", value!!).apply()

    var normalHeartRate: Float?//평소 심장 박동
        get() = prefs.getFloat("normalHeartRate", 0f)
        set(value) = prefs.edit().putFloat("normalHeartRate", value!!).apply()

    var halftime: Float//개인별 계산된 반감기 시간
        get() = prefs.getFloat("halftime", 0f )
        set(value) = prefs.edit().putFloat("halftime", value!!).apply()

    var gender: String?
        get() = prefs.getString("gender", null)
        set(value) = prefs.edit().putString("gender", value).apply()

    var awakenumber: Int?
        get() = prefs.getInt("awakenumber", 0)
        set(value) = prefs.edit().putInt("awakenumber", value!!).apply()

    var habitnumber: Int?
        get() = prefs.getInt("habitnumber", 0)
        set(value) = prefs.edit().putInt("habitnumber", value!!).apply()

    var tastenumber: Int?
        get() = prefs.getInt("tastenumber", 0)
        set(value) = prefs.edit().putInt("tastenumber", value!!).apply()

    var monCaf: Float?
        get() = prefs.getFloat("monCaf",0f)
        set(value) = prefs.edit().putFloat("monCaf", value!!).apply()
    var tueCaf: Float?
        get() = prefs.getFloat("tueCaf",0f)
        set(value) = prefs.edit().putFloat("tueCaf", value!!).apply()
    var wedCaf: Float?
        get() = prefs.getFloat("wedCaf",0f)
        set(value) = prefs.edit().putFloat("wedCaf", value!!).apply()
    var thuCaf: Float?
        get() = prefs.getFloat("thuCaf",0f)
        set(value) = prefs.edit().putFloat("thuCaf", value!!).apply()
    var friCaf: Float?
        get() = prefs.getFloat("friCaf",0f)
        set(value) = prefs.edit().putFloat("friCaf", value!!).apply()
    var satCaf: Float?
        get() = prefs.getFloat("satCaf",0f)
        set(value) = prefs.edit().putFloat("satCaf", value!!).apply()
    var sunCaf: Float?
        get() = prefs.getFloat("sunCaf",0f)
        set(value) = prefs.edit().putFloat("sunCaf", value!!).apply()

    var moreThanSensitivity: String?
        get() = prefs.getString("moreThanSensitivity",null)
        set(value) = prefs.edit().putString("moreThanSensitivity", value!!).apply()

    var isAlarmTrue: Boolean?
        get() = prefs.getBoolean("isAlarmTrue",true)
        set(value) = prefs.edit().putBoolean("isAlarmTrue", value!!).apply()


    var alarmflag: Boolean?//하루 카페인 섭취 권고량
        get() = prefs.getBoolean("alarmflag", false)
        set(value) = prefs.edit().putBoolean("alarmflag", value!!).apply()
}