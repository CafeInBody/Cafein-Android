package sso.hyeon.caffeinbody

data class CaffeineData (
    val type: Int, //카페음료:1,일반음료:2,기타:3
    val id: Int,
    val name : String,
 //   val img : String,
    val img : Int?,
//    val caffeine : Array<String>
//제조사
)