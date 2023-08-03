package sso.hyeon.caffeinbody.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Caffeine(
    val caffeine1: Int?,
    val caffeine2: Int?,
    val caffeine3: Int?
)

data class Size(
    val size1: Int?,
    val size2: Int?,
    val size3: Int?
)

@Entity
data class Drinks(
    var drinkName: String,
    @Embedded(prefix = "size_") val size: Size?,
    @Embedded(prefix = "caffeine_") val caffeine: Caffeine?,

    var category: String,
    var madeBy: String,
    var imgurl: String,
    var isCoffee: Boolean,
 // iscoffee는 카페 메뉴 중에 커피 아닌 메뉴(밀크티, 녹차라떼 같은) +일반음료
    // -> 카페인 샷이 아닌 용량별로 계산할 수 있는 메뉴 음료
    var iscafe: Boolean,
    var favorite: Boolean

){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
