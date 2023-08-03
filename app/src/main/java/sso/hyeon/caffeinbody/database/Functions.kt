package sso.hyeon.caffeinbody.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sso.hyeon.caffeinbody.CaffeineAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Functions(application: Application) {
    var drinks: LiveData<List<Drinks>> = MutableLiveData()
    var drinksNoLive : List<Drinks> = ArrayList()
    var db: DrinksDatabase
    var tag = "Functions"

    init {
        db = DrinksDatabase.getInstance(application)!!
    }

    fun selectAll(iscafe: Boolean): LiveData<List<Drinks>>{//왜 필터링 할 때마다 늘엉나지
        drinks = db.drinksDao().selectiscafe(iscafe)
        Log.e(tag, drinks.toString())
        return drinks
    }

    fun selectAll2(iscafe: Boolean, adapter: CaffeineAdapter): List<Drinks>{//왜 필터링 할 때마다 늘엉나지
        CoroutineScope(Dispatchers.IO).launch{
            drinksNoLive = db.drinksDao().selectiscafeNoLive(iscafe)
            Log.e(tag, drinksNoLive.toString())
            CoroutineScope(Dispatchers.Main).launch{
                adapter.datas.clear()
                adapter.datas.addAll(drinksNoLive)
                adapter.notifyDataSetChanged()
            }
        }
        return drinksNoLive
    }
//livedata 버전
    fun getFilteredMadeby(iscafe: Boolean, madeby: String, adapter: CaffeineAdapter): LiveData<List<Drinks>>{
        CoroutineScope(Dispatchers.IO).launch{
            drinks = db.drinksDao().selectAllConditions(iscafe, madeby)
            Log.e(tag, drinks.toString())
        }
        return drinks
    }
    //livedata 없는 버전
    fun getFilteredMadeby2(iscafe: Boolean, madeby: String, adapter: CaffeineAdapter): List<Drinks>{
        CoroutineScope(Dispatchers.IO).launch{
            drinksNoLive = db.drinksDao().selectAllConditionsNoLive(iscafe, madeby)
            Log.e(tag, drinksNoLive.toString())
            CoroutineScope(Dispatchers.Main).launch{
                adapter.datas.clear()
                adapter.datas.addAll(drinksNoLive)
                adapter.notifyDataSetChanged()
            }
        }
        return drinksNoLive
    }

    fun addDrinksDatabaseStarbucks(db: DrinksDatabase){
        CoroutineScope(Dispatchers.IO).launch{
            db.drinksDao().insertAll(
                Drinks("롤린 민트 초코 콜드 브루", Size(355, 473, 591), Caffeine(131, 197, 262), "콜드브루커피", "스타벅스", "", true, true, false),
                Drinks("나이트로 바닐라 크림", Size(355, 473, 591), Caffeine(232, 348, 464), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_nitro_vanilla_cream_ebypst.jpg", true, true, false),
                Drinks("나이트로 콜드 브루", Size(355, 473, 591), Caffeine(245, 368, 490), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_nitro_cold_brew_j9ucjp.jpg", true, true, false),
                Drinks("돌체 콜드 브루", Size(355, 473, 591), Caffeine(150, 225, 300), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_dolce_cold_brew_v5tpyt.jpg", true, true, false),
                Drinks("바닐라 크림 콜드 브루", Size(355, 473, 591), Caffeine(150, 225, 300), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490747/starbucks/star_vanilla_cream_cold_brew_aksnke.jpg", true, true, false),
                Drinks("벨벳 다크 모카 나이트로", Size(355, 473, 591), Caffeine(190, 285, 380), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490747/starbucks/star_velvet_dark_mocha_nitro_bqvqfo.jpg", true, true, false),
                Drinks("시그니처 더 블랙 콜드 브루", Size(500, 0, 0), Caffeine(680, 0, 0), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490740/starbucks/star_signature_the_black_cold_brew_fryt8v.jpg", true, true, false),
                Drinks("제주 비자림 콜드 브루", Size(0, 473, 0), Caffeine(0, 105, 0), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490743/starbucks/star_jeju_forest_cold_brew_pb2irl.jpg", true, true, false),
                Drinks("콜드 브루", Size(355, 473, 591), Caffeine(150, 225, 300), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_cold_brew_vwkygm.jpg", true, true, false),
                Drinks("콜드 브루 몰트", Size(355, 473, 591), Caffeine(190, 285, 380), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_cold_brew_malt_fkyfsj.jpg", true, true, false),
                Drinks("콜드 브루 오트 라떼", Size(355, 473, 591), Caffeine(65, 130, 130), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490740/starbucks/star_cold_brew_with_oat_milk_s3mgpp.jpg", true, true, false),
                Drinks("콜드 브루 플로트", Size(355, 473, 591), Caffeine(190, 285, 380), "콜드브루커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_cold_brew_float_esrvpk.jpg", true, true, false),

                Drinks("아이스 커피", Size(355, 473, 591), Caffeine(140, 210, 280), "브루드 커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490740/starbucks/star_iced_coffee_sewpbr.jpg", true, true, false),
                Drinks("오늘의 커피", Size(355, 473, 591), Caffeine(260, 390, 520), "브루드 커피", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_brewed_coffee_azdfpy.jpg", true, true, false),
                Drinks("에스프레소 콘 파나", Size(22, 44, 0), Caffeine(75, 113, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490739/starbucks/star_espresso_con_panna_qtzyvu.jpg", true, true, false),
                Drinks("에스프레소 마키아또", Size(22, 44, 0), Caffeine(75, 113, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490743/starbucks/star_espresso_macchiato_ffvaiv.jpg", true, true, false),
                Drinks("아이스 카페 아메리카노", Size(355, 473, 591), Caffeine(150, 225, 300), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490738/starbucks/star_iced_caffe_americano_pdecpw.jpg", true, true, false),
                Drinks("카페 아메리카노", Size(355, 473, 591), Caffeine(150, 225, 300), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490747/starbucks/star_caffe_americano_mudvyc.jpg", true, true, false),
                Drinks("아이스 카라멜 마키아또", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490742/starbucks/star_iced_caramel_macchiato_eoh3fe.jpg", true, true, false),
                Drinks("카라멜 마키아또", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490741/starbucks/star_caramel_macchiato_jg6ier.jpg", true, true, false),
                Drinks("아이스 카푸치노", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490734/starbucks/star_iced_cappuccino_vkryan.jpg", true, true, false),
                Drinks("카푸치노", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_cappuccino_laeunh.jpg", true, true, false),
                Drinks("럼 샷 코르타도", Size(273, 0, 0), Caffeine(160, 0, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490739/starbucks/star_rum_shot_cortado_tvsh3s.jpg", true, true, false),
                Drinks("바닐라 빈 라떼", Size(355, 473, 591), Caffeine(210, 420, 420), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_vanilla_bean_latte_s0vxox.jpg", true, true, false),
                Drinks("사케라또 비안코 오버 아이스", Size(355, 473, 591), Caffeine(315, 473, 630), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_shakerato_bianco_over_ice_gvxcgv.jpg", true, true, false),
                Drinks("스타벅스 돌체 라떼", Size(355, 473, 591), Caffeine(150, 300, 300), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_starbucks_dolce_latte_adnfnz.jpg", true, true, false),
                Drinks("아이스 바닐라 빈 라떼", Size(355, 473, 591), Caffeine(210, 420, 420), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_iced_vanilla_bean_latte_b8r2d8.jpg", true, true, false),
                Drinks("아이스 스타벅스 돌체 라떼", Size(355, 473, 591), Caffeine(150, 300, 300), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_iced_starbucks_dolce_latte_i0ldkx.jpg", true, true, false),
                Drinks("아이스 카페 라떼", Size(355, 473, 591), Caffeine(75, 150, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_iced_caffe_latte_lhptla.jpg", true, true, false),
                Drinks("카페 라떼", Size(355, 473, 591), Caffeine(75, 150, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_caffe_latte_jmk9wk.jpg", true, true, false),
                Drinks("아이스 카페 모카", Size(355, 473, 591), Caffeine(95, 143, 190), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_iced_caffe_mocha_ndvnyz.jpg", true, true, false),
                Drinks("아이스 화이트 초콜릿 모카", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490743/starbucks/star_iced_white_chocolate_mocha_qhacbg.jpg", true, true, false),
                Drinks("카페 모카", Size(355, 473, 591), Caffeine(95, 143, 190), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_caffe_mocha_muax25.jpg", true, true, false),
                Drinks("화이트 초콜릿 모카", Size(355, 473, 591), Caffeine(75, 113, 150), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_white_chocolate_mocha_ts0yhj.jpg", true, true, false),
                Drinks("바닐라 플랫 화이트", Size(355, 473, 591), Caffeine(260, 390, 520), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_vanilla_flat_white_ne9adt.jpg", true, true, false),
                Drinks("바닐라 스타벅스 더블 샷", Size(207, 0, 0), Caffeine(150, 0, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490736/starbucks/star_vanilla_starbucks_double_shot_vcgnjo.jpg", true, true, false),
                Drinks("블론드 바닐라 더블 샷 마키아또", Size(355, 473, 591), Caffeine(170, 255, 340), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490740/starbucks/star_blonde_vanilla_double_shot_macchiato_mvsf0x.jpg", true, true, false),
                Drinks("사케라또 아포가토", Size(355, 473, 591), Caffeine(210, 315, 420), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490736/starbucks/star_shakerato_affogato_ghmuoo.jpg", true, true, false),
                Drinks("스파클링 시트러스 에스프레소", Size(355, 473, 591), Caffeine(105, 158, 210), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_sparkling_citrus_espresso_y0d10k.jpg", true, true, false),
                Drinks("아이스 블론드 바닐라 더블 샷 마키아또", Size(355, 473, 591), Caffeine(170, 255, 340), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_iced_blonde_vanilla_double_shot_macchiato_kqc4wt.jpg", true, true, false),
                Drinks("에스프레소", Size(22, 44, 0), Caffeine(75, 113, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490739/starbucks/star_espresso_nvrlge.jpg", true, true, false),
                Drinks("커피 스타벅스 더블 샷", Size(207, 0, 0), Caffeine(150, 0, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490735/starbucks/star_coffee_starbucks_double_shot_dfeicy.jpg", true, true, false),
                Drinks("클래식 아포가토", Size(355, 473, 591), Caffeine(210, 315, 420), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_classic_affogato_hry2vl.jpg", true, true, false),
                Drinks("헤이즐넛 스타벅스 더블 샷", Size(207, 0, 0), Caffeine(150, 0, 0), "에스프레소", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490743/starbucks/star_hazelnut_starbucks_double_shot_d0e2sk.jpg", true, true, false),
                Drinks("더블 에스프레소 칩 프라푸치노", Size(355, 473, 591), Caffeine(130, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_double_espresso_chip_frappuccino_blbsog.jpg", false, true, false),
                Drinks("모카 프라푸치노", Size(355, 473, 591), Caffeine(90, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490740/starbucks/star_mocha_frappuccino_rlhlpp.jpg", false, true, false),
                Drinks("에스프레소 프라푸치노", Size(355, 473, 591), Caffeine(120, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490735/starbucks/star_espresso_frappuccino_aolnaz.jpg", false, true, false),
                Drinks("자바 칩 프라푸치노", Size(355, 473, 591), Caffeine(100, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490738/starbucks/star_java_chip_frappuccino_yt4alr.jpg", false, true, false),
                Drinks("카라멜 프라푸치노", Size(355, 473, 591), Caffeine(85, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_caramel_frappuccino_eusfxs.jpg", false, true, false),
                Drinks("화이트 초콜릿 모카 프라푸치노", Size(355, 473, 591), Caffeine(85, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_white_chocolate_mocha_frappuccino_nslosg.jpg", false, true, false),
                Drinks("제주 유기농 말차로 만든 크림 프라푸치노", Size(355, 473, 591), Caffeine(60, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490747/starbucks/star_malcha_cream_frappuccino_from_jeju_organic_farm_cevqcb.jpg", false, true, false),
                Drinks("초콜릿 크림 칩 프라푸치노", Size(355, 473, 591), Caffeine(10, 0, 0), "프라푸치노", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490741/starbucks/star_chocolate_cream_chip_frappuccino_p3ojb8.jpg", false, true, false),
                Drinks("레드 파워 스매시 블렌디드", Size(0, 473, 0), Caffeine(0, 17, 0), "블렌디드", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490735/starbucks/star_red_power_smash_blended_sqbtxq.jpg", false, true, false),
                Drinks("망고 패션 프루트 블렌디드", Size(355, 473, 591), Caffeine(35, 0, 0), "블렌디드", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_mango_passion_fruit_blended_g90r6e.jpg", false, true, false),
                Drinks("블랙 티 레모네이드 피지오", Size(355, 473, 591), Caffeine(30, 0, 0), " 스타벅스 피지오", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490743/starbucks/star_black_tea_lemonade_starbucks_fizzio_hmokdi.jpg", false, true, false),
                Drinks("쿨 라임 피지오", Size(355, 473, 591), Caffeine(110, 0, 0), " 스타벅스 피지오", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490736/starbucks/star_cool_lime_starbucks_fizzio_oi8d3y.jpg", false, true, false),
                Drinks("포멜로 플로우 그린티", Size(355, 473, 591), Caffeine(3, 0, 0), "티(티바나)", "스타벅스", "", false, true, false),
                Drinks("별궁 오미자 유스베리 티", Size(0, 473, 0), Caffeine(0, 25, 0), "티(티바나)", "스타벅스", "", false, true, false),
                Drinks("아이스 얼 그레이 티", Size(355, 473, 591), Caffeine(50, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490736/starbucks/star_iced_earl_grey_brewed_tea_cgppsi.jpg", false, true, false),
                Drinks("아이스 유스베리 티", Size(355, 473, 591), Caffeine(20, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490742/starbucks/star_iced_youthberry_brewed_tea_m3v5tz.jpg", false, true, false),
                Drinks("아이스 잉글리쉬 브렉퍼스트 티", Size(355, 473, 591), Caffeine(40, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490739/starbucks/star_iced_english_breakfast_brewed_tea_jo33uh.jpg", false, true, false),
                Drinks("아이스 제주 유기 녹차", Size(355, 473, 591), Caffeine(16, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490747/starbucks/star_iced_jeju_organic_green_tea_v5i5av.jpg", false, true, false),
                Drinks("얼 그레이 티", Size(355, 473, 591), Caffeine(70, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490736/starbucks/star_earl_grey_brewed_tea_iwbdug.jpg", false, true, false),
                Drinks("유스베리 티", Size(355, 473, 591), Caffeine(20, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_youthberry_brewed_tea_kr0mg4.jpg", false, true, false),
                Drinks("잉글리쉬 브렉퍼스트 티", Size(355, 473, 591), Caffeine(70, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_english_breakfast_brewed_tea_k6sf55.jpg", false, true, false),
                Drinks("자몽 허니 블랙 티", Size(355, 473, 591), Caffeine(70, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_grapefruit_honey_black_tea_rwe6j4.jpg", false, true, false),
                Drinks("제주 유기 녹차", Size(355, 473, 591), Caffeine(16, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490746/starbucks/star_jeju_organic_green_tea_a2m4jo.jpg", false, true, false),
                Drinks("아이스 별궁 오미자 유스베리 티", Size(355, 473, 591), Caffeine(15, 0, 0), "티(티바나)", "스타벅스", "", false, true, false),
                Drinks("아이스 자몽 허니 블랙 티", Size(355, 473, 591), Caffeine(30, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490735/starbucks/star_iced_grapefruit_honey_black_tea_bdj4mg.jpg", false, true, false),
                Drinks("제주 키위 오션 그린티", Size(0, 473, 0), Caffeine(0, 10, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490742/starbucks/star_jeju_kiwi_ocean_green_tea_mds0e4.jpg", false, true, false),
                Drinks("돌체 블랙 밀크 티", Size(355, 473, 591), Caffeine(60, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490742/starbucks/star_dolce_black_milk_tea_jm08zd.jpg", false, true, false),
                Drinks("아이스 돌체 블랙 밀크 티", Size(355, 473, 591), Caffeine(35, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490741/starbucks/star_iced_dolce_black_milk_tea_cv9jnz.jpg", false, true, false),
                Drinks("아이스 제주 유기농 말차로 만든 라떼", Size(355, 473, 591), Caffeine(60, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_iced_malcha_latte_from_jeju_organic_farm_bp4h5e.jpg", false, true, false),
                Drinks("아이스 차이 티 라떼", Size(355, 473, 591), Caffeine(70, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490744/starbucks/star_iced_chai_tea_latte_cbebjz.jpg", false, true, false),
                Drinks("아이스 허니 얼 그레이 밀크 티", Size(0, 473, 0), Caffeine(0, 52, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490735/starbucks/star_iced_honey_earl_grey_milk_tea_lyws9o.jpg", false, true, false),
                Drinks("제주 유기농 말차로 만든 라떼", Size(355, 473, 591), Caffeine(60, 0, 0), "티(티바나)", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490748/starbucks/star_iced_malcha_latte_from_jeju_organic_farm_bp4h5e.jpg", false, true, false),
                Drinks("차이 티 라떼", Size(355, 473, 591), Caffeine(70, 0, 0), "티(티바나)", "스타벅스", "", false, true, false),
                Drinks("허니 얼 그레이 밀크 티", Size(0, 473, 0), Caffeine(0, 70, 0), "티(티바나)", "스타벅스", "", false, true, false),
                Drinks("레드 파워 스매셔", Size(0, 473, 0), Caffeine(0, 25, 0), "기타 제조 음료", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490741/starbucks/star_red_power_smasher_hp1q5z.jpg", false, true, false),
                Drinks("시그니처 핫 초콜릿", Size(355, 473, 591), Caffeine(15, 0, 0), "기타 제조 음료", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490737/starbucks/star_signature_hot_chocolate_kqm6dw.jpg", false, true, false),
                Drinks("아이스 시그니처 초콜릿", Size(355, 473, 591), Caffeine(15, 0, 0), "기타 제조 음료", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490745/starbucks/star_iced_signature_chocolate_exfudk.jpg", false, true, false),
            )
            Log.e("testRoom", "스타벅스 starbucks")
        }
    }

    fun addDrinksDatabaseHollys(db: DrinksDatabase){
        CoroutineScope(Dispatchers.IO).launch{
            db.drinksDao().insertAll(
                Drinks("디카페인 아메리카노", Size(354, 0, 0), Caffeine(7, 0, 0), "커피", "할리스", "", true, true, false),
                Drinks("디카페인 카페라떼", Size(354, 0, 0), Caffeine(7, 0, 0), "커피", "할리스", "", true, true, false),
                Drinks("디카페인 바닐라 딜라이트", Size(354, 0, 0), Caffeine(20, 0, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490731/hollys/hol_double_shot_vanilla_delight_onyp8w.png", true, true, false),
                Drinks("프리미엄 블렌드 아메리카노", Size(354, 472, 0), Caffeine(233, 350, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490730/hollys/hol_premium_blend_americano_iqadb4.png", true, true, false),
                Drinks("콜드브루 딜라이트", Size(354, 472, 0), Caffeine(148, 222, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_cold_brew_delight_kanedm.png", true, true, false),
                Drinks("더블샷 바닐라 딜라이트", Size(354, 0, 0), Caffeine(169, 0, 0), "커피", "할리스", "", true, true, false),
                Drinks("프리미엄 블렌드 딥라떼", Size(354, 472, 591), Caffeine(111, 167, 222), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_premium_blend_deep_latte_xh21gy.png", true, true, false),
                Drinks("바닐라 딜라이트", Size(354, 472, 591), Caffeine(80, 120, 160), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_vanilla_delight_gkrpvp.png", true, true, false),
                Drinks("리스트레또 딜라이트", Size(354, 472, 0), Caffeine(93, 140, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490731/hollys/hol_ristretto_delight_yctszo.png", true, true, false),
                Drinks("콜드브루 라떼", Size(354, 472, 591), Caffeine(148, 222, 296), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490732/hollys/hol_cold_brew_latte_x7ada1.png", true, true, false),
                Drinks("콜드브루", Size(354, 472, 591), Caffeine(137, 206, 274), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_cold_brew_nzbip7.png", true, true, false),
                Drinks("카라멜 마키아또", Size(354, 472, 0), Caffeine(127, 191, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_caramel_macchiato_wt6v8k.png", true, true, false),
                Drinks("카페모카", Size(354, 472, 0), Caffeine(132, 198, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490730/hollys/hol_caffe_mocha_avgbnp.png", true, true, false),
                Drinks("카푸치노", Size(354, 472, 0), Caffeine(127, 191, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490734/hollys/hol_cappuccino_yflwry.png", true, true, false),
                Drinks("카페라떼", Size(354, 472, 591), Caffeine(127, 191, 254), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490730/hollys/hol_caffe_latte_rhz4ou.png", true, true, false),
                Drinks("아메리카노", Size(354, 472, 591), Caffeine(114, 171, 228), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_caffe_americano_adu1re.png", true, true, false),
                Drinks("아포가토", Size(150, 0, 0), Caffeine(93, 0, 0), "커피", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490728/hollys/hol_affogato_cqgtlz.png", true, true, false),
                Drinks("에스프레소", Size(25, 0, 0), Caffeine(61, 0, 0), "커피", "할리스", "", true, true, false),
                Drinks("민트초코", Size(354, 472, 0), Caffeine(1, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490733/hollys/hol_mint_chocolate_pn9bhm.png", false, true, false),
                Drinks("그린티라떼", Size(354, 472, 0), Caffeine(50, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490732/hollys/hol_green_tea_latte_vhbnkg.png", false, true, false),
                Drinks("밀크티라떼", Size(354, 472, 0), Caffeine(72, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490730/hollys/hol_milk_tea_latte_zhcvu4.png", false, true, false),
                Drinks("핫초코", Size(354, 472, 0), Caffeine(8, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490729/hollys/hol_hot_chocolate_uchrum.png", false, true, false),
                Drinks("화이트초코", Size(354, 472, 0), Caffeine(23, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490732/hollys/hol_white_chocolate_ewrzuq.png", false, true, false),
                Drinks("복숭아 얼그레이", Size(354, 472, 0), Caffeine(33, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490728/hollys/hol_peach_earl_grey_oiia95.png", false, true, false),
                Drinks("해남 녹차", Size(354, 0, 0), Caffeine(25, 0, 0), "라떼·초콜릿·티", "할리스", "", false, true, false),
                Drinks("얼그레이", Size(354, 0, 0), Caffeine(17, 0, 0), "라떼·초콜릿·티", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490728/hollys/hol_earl_grey_btwrmg.png", false, true, false),
                Drinks("민트 초코칩 할리치노", Size(354, 0, 0), Caffeine(1, 0, 0), "할리치노·빙수", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490731/hollys/hol_mint_chocochip_hollyccino_qxbunq.png", false, true, false),
                Drinks("그린티 할리치노", Size(354, 472, 0), Caffeine(99, 0, 0), "할리치노·빙수", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490728/hollys/hol_green_tea_hollyccino_xcjdgv.png", false, true, false),
                Drinks("다크초코칩 할리치노", Size(354, 472, 0), Caffeine(59, 0, 0), "할리치노·빙수", "할리스", "", false, true, false),
                Drinks("콜드브루 할리치노", Size(354, 472, 0), Caffeine(74, 0, 0), "할리치노·빙수", "할리스", "https://res.cloudinary.com/cafeinbody/image/upload/v1658490730/hollys/hol_coldbrew_hollyccino_pgk8gc.png", false, true, false),
                Drinks("데이드림 HOT", Size(283, 0, 0), Caffeine(235, 0, 0), "MD식품", "할리스", "", false, true, false),
                Drinks("데이드림 ICED", Size(340, 0, 0), Caffeine(177, 0, 0), "MD식품", "할리스", "", false, true, false),
                Drinks("이클립스 HOT", Size(283, 0, 0), Caffeine(235, 0, 0), "MD식품", "할리스", "", false, true, false),
                Drinks("이클립스 ICED", Size(340, 0, 0), Caffeine(177, 0, 0), "MD식품", "할리스", "", false, true, false),
                Drinks("과테말라 싱글오리진 HOT", Size(283, 0, 0), Caffeine(237, 0, 0), "MD식품", "할리스", "", false, true, false),
                Drinks("과테말라 싱글오리진 ICED", Size(340, 0, 0), Caffeine(151, 0, 0), "MD식품", "할리스", "", false, true, false),)
    }}

    fun addDrinksDatabaseTwosome(db: DrinksDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            db.drinksDao().insertAll(
                Drinks("스페니쉬 연유 카페라떼", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("카라멜 마키아또", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886978/twosome/two_iced_caramel_macchiato_o0f5v3.png", true, true, false),
                Drinks("바닐라 카페라떼", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("카페 모카", Size(355, 414, 474), Caffeine(197, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489393/twosome/two_cafe_mocha_googj9.jpg", true, true, false),
                Drinks("카페라떼", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489393/twosome/two_cafe_latte_od1lmn.jpg", true, true, false),
                Drinks("카푸치노", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("숏 카페라떼", Size(237, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489393/twosome/two_short_cafe_latte_ngjpta.jpg", true, true, false),
                Drinks("아메리카노", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489393/twosome/two_americano_yq5ayq.jpg", true, true, false),
                Drinks("롱블랙", Size(355, 414, 474), Caffeine(177, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886975/twosome/two_long_black_zitqwj.png", true, true, false),
                Drinks("에스프레소", Size(355, 414, 474), Caffeine(89, 177, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489394/twosome/two_espresso_rwgixd.png", true, true, false),
                Drinks("에스프레소 콘 파냐", Size(355, 414, 474), Caffeine(89, 177, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("에스프레소 마키아또", Size(355, 414, 474), Caffeine(89, 177, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("콜드브루", Size(355, 414, 474), Caffeine(196, 265, 0), "커피", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886977/twosome/two_coldbrew_chdafx.png", true, true, false),
                Drinks("콜드브루 라떼", Size(355, 414, 474), Caffeine(196, 265, 0), "커피", "투썸플레이스", "", true, true, false),
                Drinks("1837 블랙티", Size(0, 414, 474), Caffeine(0, 45, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("프렌치 얼그레이", Size(0, 414, 474), Caffeine(0, 62, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("그나와 민트티", Size(0, 414, 474), Caffeine(0, 30, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("허니 레몬티", Size(355, 414, 474), Caffeine(48, 0, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("애플 민트티", Size(355, 414, 474), Caffeine(30, 0, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("애플 민트 아이스티", Size(355, 414, 474), Caffeine(33, 0, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("허니 레몬 아이스티", Size(355, 414, 474), Caffeine(40, 0, 0), "티", "투썸플레이스", "", false, true, false),
                Drinks("로얄 밀크티", Size(355, 414, 474), Caffeine(101, 133, 0), "티 라떼", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886978/twosome/two_royal_milktea_hqwl8r.png", false, true, false),
                Drinks("그린티 라떼", Size(355, 414, 474), Caffeine(55, 68, 0), "티 라떼", "투썸플레이스", "", false, true, false),
                Drinks("아이스 버블 밀크티", Size(355, 414, 474), Caffeine(78, 98, 0), "티 라떼", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886974/twosome/two_bubble_milktea_gkwmbx.png", false, true, false),
                Drinks("아이스 버블 그린티 라떼", Size(355, 414, 474), Caffeine(53, 69, 0), "티 라떼", "투썸플레이스", "", false, true, false),
                Drinks("아이스 로얄 밀크티", Size(355, 414, 474), Caffeine(128, 171, 0), "티 라떼", "투썸플레이스", "", false, true, false),
                Drinks("아이스 그린티 라떼", Size(355, 414, 474), Caffeine(53, 69, 0), "티 라떼", "투썸플레이스", "", false, true, false),
                Drinks("바닐라 아포가토", Size(355, 414, 474), Caffeine(89, 0, 0), "아이스크림", "투썸플레이스", "", false, true, false),
                Drinks("제주 말차 프라페", Size(355, 414, 474), Caffeine(106, 135, 0), "아이스 블랜디드", "투썸플레이스", "", false, true, false),
                Drinks("모카칩 커피 프라페", Size(355, 414, 474), Caffeine(270, 307, 0), "아이스 블랜디드", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886976/twosome/two_mocha_chip_frappe_apip1g.png", false, true, false),
                Drinks("카라멜 커피 프라페", Size(355, 414, 474), Caffeine(284, 353, 0), "아이스 블랜디드", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886976/twosome/two_caramel_frappe_pemzki.png", false, true, false),
                Drinks("로얄 밀크티 쉐이크", Size(355, 414, 474), Caffeine(84, 110, 0), "아이스 블랜디드", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886976/twosome/two_royal_milktea_shake_q182jk.png", false, true, false),
                Drinks("초코 밀크 쉐이크", Size(355, 414, 474), Caffeine(80, 0, 0), "아이스 블랜디드", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886976/twosome/two_choco_shake_id5qyx.png", false, true, false),
                Drinks("커피 밀크 쉐이크", Size(355, 414, 474), Caffeine(182, 0, 0), "아이스 블랜디드", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1659886978/twosome/two_coffe_shake_qdi6ds.png", false, true, false),
                Drinks("초콜릿 라떼", Size(355, 414, 474), Caffeine(16, 21, 0), "아이스 블랜디드", "투썸플레이스", "", false, true, false),
                Drinks("아이스 초콜릿 라떼", Size(355, 414, 474), Caffeine(19, 25, 0), "아이스 블랜디드", "투썸플레이스", "", false, true, false),
                Drinks("흑임자 카페라떼", Size(355, 414, 474), Caffeine(177, 265, 0), "뉴트로 테이스트", "투썸플레이스", "https://res.cloudinary.com/cafeinbody/image/upload/v1660489393/twosome/two_heukimja_latte_gpudsp.jpg", false, true, false),
                Drinks("달고나 카페라떼", Size(355, 414, 474), Caffeine(50, 60, 0), "뉴트로 테이스트", "투썸플레이스", "", false, true, false),
                Drinks("아이스 흑임자 카페라떼", Size(355, 414, 474), Caffeine(177, 265, 0), "뉴트로 테이스트", "투썸플레이스", "", false, true, false),
                Drinks("아이스 달고나 카페라떼", Size(355, 414, 474), Caffeine(58, 70, 0), "뉴트로 테이스트", "투썸플레이스", "", false, true, false),)
        }}

        fun addDrinksDatabaseEdiya(db: DrinksDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                db.drinksDao().insertAll(
                    Drinks("아인슈페너", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("콜드브루 아인슈페너", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("디카페인 콜드브루 아인슈페너", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("블랙모카슈페너", Size(384, 0, 0), Caffeine(107, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("디카페인 콜드브루 아메리카노", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883002/ediya/ediya_iced_decaffeine_coldbrew_americano_mryeaa.png", true, true, false),
                    Drinks("디카페인 콜드브루 라뗴", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882997/ediya/ediya_decaffeine_coldbrew_latte_v759ji.png", true, true, false),
                    Drinks("디카페인 콜드브루 화이트 비엔나", Size(384, 0, 0), Caffeine(6, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883005/ediya/ediya_iced_decaffeine_cold_brew_white_vienna_ipbofb.png", true, true, false),
                    Drinks("디카페인 콜드브루 니트로", Size(384, 0, 0), Caffeine(10, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883007/ediya/ediya_decaffeine_coldbrew_nitro_efvpsr.png", true, true, false),
                    Drinks("디카페인 흑당 콜드브루", Size(384, 650, 0), Caffeine(6, 9, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883004/ediya/ediya_iced_decaffeine_black_sugar_cold_brew_qtdmed.png", true, true, false),
                    Drinks("디카페인 연유 콜드브루", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882997/ediya/ediya_decaffeine_coldbrew_latte_v759ji.png", true, true, false),
                    Drinks("디카페인 콜드브루 티라미수", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("디카페인 콜드브루 크림넛", Size(384, 0, 0), Caffeine(9, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_iced_decaffeine_coldbrew_creamnut_bwrqih.png", true, true, false),
                    Drinks("디카페인 버블 흑당 콜드브루", Size(384, 0, 0), Caffeine(6, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883005/ediya/ediya_decaffeine_blacksugar_coldbrew_kpavqj.png", true, true, false),
                    Drinks("콜드브루 티라미수", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("콜드브루 크림넛", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_iced_cold_brew_creamnut_hcp1pp.png", true, true, false),
                    Drinks("흑당 콜드브루", Size(384, 650, 0), Caffeine(65, 130, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883000/ediya/ediya_iced_decaffeine_black_sugar_coldbrew_ti7urk.png", true, true, false),
                    Drinks("버블 흑당 콜드브루", Size(384, 650, 0), Caffeine(65, 130, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_iced_bubble_blacksugar_coldbrew_u4twxh.png", true, true, false),
                    Drinks("연유 카페라떼", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883005/ediya/ediya_iced_condensed_milk_cafe_latte_de3lua.png", true, true, false),
                    Drinks("연유 콜드브루", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883004/ediya/ediya_iced_condensed_milk_coldbrew_x5edni.png", true, true, false),
                    Drinks("콜드브루 아메리카노", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883003/ediya/ediya_iced_coldbrew_americano_xa3dj9.png", true, true, false),
                    Drinks("아포가토 오리지널", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_affogato_original_gvutea.png", true, true, false),
                    Drinks("아포가토 콜드브루 바닐라 모카", Size(384, 0, 0), Caffeine(68, 0, 0), "커피", "이디야", "", true, true, false),
                    Drinks("콜드브루 라떼", Size(384, 0, 0), Caffeine(130, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_iced_coldbrew_latte_zlsl0c.png", true, true, false),
                    Drinks("콜드브루 화이트 비엔나", Size(384, 0, 0), Caffeine(65, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882996/ediya/ediya_iced_cold_brew_white_vienna_nusrqx.png", true, true, false),
                    Drinks("콜드브루 니트로", Size(384, 0, 0), Caffeine(155, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883002/ediya/ediya_cold_brew_nitro_tihj0f.png", true, true, false),
                    Drinks("에스프레소", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883008/ediya/ediya_espresso_l9u0ba.png", true, true, false),
                    Drinks("에스프레소 마끼아또", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_espresso_machiato_z3xhtg.png", true, true, false),
                    Drinks("에스프레소 콘파냐", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882999/ediya/ediya_espresso_conpanya_rggz1f.png", true, true, false),
                    Drinks("카페 아메리카노", Size(384, 650, 0), Caffeine(103, 206, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_iced_cafe_americano_rgtx2n.png", true, true, false),
                    Drinks("카페라떼", Size(384, 650, 0), Caffeine(103, 206, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883005/ediya/ediya_iced_cafe_latte_hrse37.png", true, true, false),
                    Drinks("카푸치노", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883007/ediya/ediya_capuccino_mqyrhk.png", true, true, false),
                    Drinks("카페모카", Size(384, 650, 0), Caffeine(99, 197, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_cafe_mocha_eu9u52.png", true, true, false),
                    Drinks("카라멜 마끼아또", Size(384, 650, 0), Caffeine(91, 206, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882996/ediya/ediya_caramel_machiato_jltwpl.png", true, true, false),
                    Drinks("바닐라라떼", Size(384, 650, 0), Caffeine(115, 229, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883002/ediya/ediya_vanila_latte_pjaztz.png", true, true, false),
                    Drinks("화이트 초콜릿모카", Size(384, 0, 0), Caffeine(103, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883008/ediya/ediya_white_chocolate_mocha_alpqqp.png", true, true, false),
                    Drinks("민트모카", Size(384, 0, 0), Caffeine(124, 0, 0), "커피", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882998/ediya/ediya_iced_mint_mocha_mrv2h3.png", true, true, false),
                    Drinks("크림 달고나 밀크티", Size(384, 0, 0), Caffeine(18, 0, 0), "라떼·초콜릿·티", "이디야", "", false, true, false),
                    Drinks("달고나 밀크티", Size(384, 0, 0), Caffeine(18, 0, 0), "라떼·초콜릿·티", "이디야", "", false, true, false),
                    Drinks("버블 크림 밀크티", Size(384, 0, 0), Caffeine(18, 0, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_iced_bubble_cream_milktea_ehjsyc.png", false, true, false),
                    Drinks("아이스 초콜릿", Size(384, 650, 0), Caffeine(7, 14, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882995/ediya/ediya_iced_chocolate_ddzl6k.png", false, true, false),
                    Drinks("핫 초콜릿", Size(384, 650, 0), Caffeine(7, 14, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883003/ediya/ediya_hot_chocolate_pnpdum.png", false, true, false),
                    Drinks("녹차라떼", Size(384, 650, 0), Caffeine(58, 113, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883009/ediya/ediya_iced_greentea_latte_vokiks.png", false, true, false),
                    Drinks("민트초코", Size(384, 0, 0), Caffeine(21, 0, 0), "라떼·초콜릿·티", "이디야", "", false, true, false),
                    Drinks("토피넛 라떼", Size(384, 650, 0), Caffeine(19, 27, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883000/ediya/ediya_iced_toffeenut_latte_lgtg4f.png", false, true, false),
                    Drinks("복숭아 아이스티 ", Size(384, 650, 0), Caffeine(11, 21, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_peach_icedtea_gbixvq.png", false, true, false),
                    Drinks("레몬 아이스티", Size(384, 650, 0), Caffeine(14, 28, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882999/ediya/ediya_lemon_iced_tea_gqhsmc.png", false, true, false),
                    Drinks("얼그레이 홍차", Size(384, 0, 0), Caffeine(25, 0, 0), "라떼·초콜릿·티", "이디야", "", false, true, false),
                    Drinks("로즈 자스민 티", Size(384, 0, 0), Caffeine(25, 0, 0), "라떼·초콜릿·티", "이디야", "", false, true, false),
                    Drinks("밀크티", Size(384, 0, 0), Caffeine(56, 0, 0), "라떼·초콜릿·티", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882993/ediya/ediya_milktea_uuxfgf.png", false, true, false),
                    Drinks("커피 플랫치노", Size(384, 0, 0), Caffeine(103, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883002/ediya/ediya_coffee_flatccino_ryzmrm.png", false, true, false),
                    Drinks("모카 플랫치노", Size(384, 0, 0), Caffeine(107, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883001/ediya/ediya_mocha_flatccino_hd3iyz.png", false, true, false),
                    Drinks("카라멜 플랫치노", Size(384, 0, 0), Caffeine(103, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882994/ediya/ediya_caramel_flatccino_vrwukm.png", false, true, false),
                    Drinks("녹차 플랫치노", Size(384, 0, 0), Caffeine(58, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883001/ediya/ediya_greentea_flatccino_ngmyue.png", false, true, false),
                    Drinks("초콜릿 칩 플랫치노", Size(384, 0, 0), Caffeine(35, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659883007/ediya/ediya_chocolate_chip_flatccino_hbuatm.png", false, true, false),
                    Drinks("민트 초콜릿 칩 플랫치노", Size(384, 0, 0), Caffeine(44, 0, 0), "플랫치노", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1659882998/ediya/ediya_mint_chocolate_chip_flatccino_cpi3u8.png", false, true, false),)
            }
        }

        fun addDrinksDatabasePaiks(db: DrinksDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                db.drinksDao().insertAll(
                    Drinks("더블에스프레소", Size(60, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484623/paik/paik_double_espresso_zjocsb.png", true, true, false),
                    Drinks("앗!메리카노(hot)", Size(400, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_hot_americano_rs6se8.png", true, true, false),
                    Drinks("앗!메리카노(iced)", Size(625, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_iced_americano_axet5j.png", true, true, false),
                    Drinks("원조커피(hot)", Size(375, 0, 0), Caffeine(390, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_hot_original_coffee_mvspeg.png", true, true, false),
                    Drinks("원조커피(iced)", Size(625, 0, 0), Caffeine(390, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_iced_original_coffe_oh6ah3.png", true, true, false),
                    Drinks("달달연유라떼(hot)", Size(425, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_condensedmilk_latte_hot_trysmg.png", true, true, false),
                    Drinks("달달연유라떼(iced)", Size(600, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_condensedmilk_latte_iced_wjowda.png", true, true, false),
                    Drinks("빽's 카페라떼(hot)", Size(300, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484616/paik/paik_cafe_latte_hot_s68bl1.png", true, true, false),
                    Drinks("빽's 카페라떼(iced)", Size(625, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_cafe_latte_iced_uwi5dp.png", true, true, false),
                    Drinks("블랙펄카페라떼(iced)", Size(634, 0, 0), Caffeine(119, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_blackpearl_cafelatte_iced_ex8qqz.png", true, true, false),
                    Drinks("바닐라라떼(hot)", Size(325, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_vanila_latte_hot_tgl987.png", true, true, false),
                    Drinks("바닐라라떼(iced)", Size(600, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_vanila_latte_iced_xwwvly.png", true, true, false),
                    Drinks("카페모카(hot)", Size(350, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_cafe_mocha_komjpp.png", true, true, false),
                    Drinks("카페모카(iced)", Size(625, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484616/paik/paik_iced_cafe_mocha_rzo226.png", true, true, false),
                    Drinks("카라멜마끼아또(hot)", Size(350, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_caramel_macchiato_f9teza.png", true, true, false),
                    Drinks("카라멜마끼아또(iced)", Size(625, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_iced_caramel_macchiato_vh8c8t.png", true, true, false),
                    Drinks("코코넛카페라떼(HOT)", Size(335, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484622/paik/paik_coconut_cafe_latte_m6udqv.png", true, true, false),
                    Drinks("코코넛카페라떼(ICED)", Size(615, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_iced_coconut_cafe_latte_v3nvru.png", true, true, false),
                    Drinks("코코넛커피스무디(ICED)", Size(549, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_iced_coconut_coffee_smoothie_zacrev.png", true, true, false),
                    Drinks("아이스크림카페라떼(ICED)", Size(650, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_iced_ice_cream_cafe_latte_sw93ve.png", true, true, false),
                    Drinks("아이스크림바닐라라떼(ICED)", Size(570, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484622/paik/paik_ice_cream_vanila_latte_adn1lv.png", true, true, false),
                    Drinks("아이스크림카페모카(ICED)", Size(570, 0, 0), Caffeine(237, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_iced_ice_cream_cafe_mocha_ev27bj.png", true, true, false),
                    Drinks("콜드브루라떼", Size(600, 0, 0), Caffeine(195, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_coldbrew_latte_exihwr.png", true, true, false),
                    Drinks("디카페인 콜드브루라떼", Size(600, 0, 0), Caffeine(15, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_decaffeine_coldbrew_latte_psktk6.png", true, true, false),
                    Drinks("콜드브루", Size(600, 0, 0), Caffeine(195, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_cold_brew_jcivzt.png", true, true, false),
                    Drinks("디카페인 콜드브루", Size(600, 0, 0), Caffeine(15, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_decaffeine_coldbrew_nyyt4u.png", true, true, false),
                    Drinks("콜드브루라떼(연유)", Size(600, 0, 0), Caffeine(195, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_cold_brew_latte_condensed_milk_cfmq5j.png", true, true, false),
                    Drinks("디카페인 콜드브루라떼(연유)", Size(600, 0, 0), Caffeine(15, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484616/paik/paik_decaffeine_coldbrew_latte_condensedmilk_zhn2yp.png", true, true, false),
                    Drinks("콜드브루라떼(흑당)", Size(600, 0, 0), Caffeine(195, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_cold_brew_latte_black_sugar_cqymtm.png", true, true, false),
                    Drinks("디카페인 콜드브루라떼(흑당)", Size(600, 0, 0), Caffeine(15, 0, 0), "커피", "빽다방", "", true, true, false),
                    Drinks("아이스티샷추가(아샷추)", Size(600, 0, 0), Caffeine(93, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_icetea_plus_shot_d5qzvf.png", true, true, false),
                    Drinks("빽사이즈 앗!메리카노(iced)", Size(950, 0, 0), Caffeine(474, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_paiksize_americano_c31n7l.png", true, true, false),
                    Drinks("빽사이즈 원조커피(iced)", Size(950, 0, 0), Caffeine(597, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357434/paik/paik_paiksize_iced_original_coffee_lxl0qs.png", true, true, false),
                    Drinks("빽사이즈 빽's 카페라떼(iced)", Size(950, 0, 0), Caffeine(474, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_paiksize_cafe_latte_ygekbg.png", true, true, false),
                    Drinks("빽사이즈 아이스티샷추가(iced)", Size(900, 0, 0), Caffeine(167, 0, 0), "커피", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_paiksize_icedtea_plus_shot_vew5if.png", true, true, false),
                    Drinks("녹차라떼(hot)", Size(400, 0, 0), Caffeine(208, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_greentea_latte_hot_fk1mvd.png", false, true, false),
                    Drinks("녹차라떼(iced)", Size(600, 0, 0), Caffeine(152, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_greentea_latte_iced_w64qge.png", false, true, false),
                    Drinks("토피넛라떼(hot)", Size(350, 0, 0), Caffeine(51, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_toffeenut_latte_liiscx.png", false, true, false),
                    Drinks("토피넛라떼(iced)", Size(650, 0, 0), Caffeine(51, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_iced_toffeenut_latte_nft2iq.png", false, true, false),
                    Drinks("밀크티(hot)", Size(375, 0, 0), Caffeine(133, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_milktea_hot_tbw17k.png", false, true, false),
                    Drinks("밀크티(iced)", Size(625, 0, 0), Caffeine(133, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_milktea_iced_qddgsl.png", false, true, false),
                    Drinks("블랙펄밀크티(iced)", Size(643, 0, 0), Caffeine(77, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_blackpearl_milktea_iced_jbv6pv.png", false, true, false),
                    Drinks("달콤아이스티", Size(600, 0, 0), Caffeine(51, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_iced_tea_cguifz.png", false, true, false),
                    Drinks("피치우롱스위트티(hot)", Size(365, 0, 0), Caffeine(65, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_peach_oorong_sweet_tea_ncfkri.png", false, true, false),
                    Drinks("피치우롱스위트티(iced)", Size(593, 0, 0), Caffeine(71, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_iced_peach_oorong_sweet_tea_qydtsj.png", false, true, false),
                    Drinks("우롱티(hot)", Size(400, 0, 0), Caffeine(92, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_oorong_tea_o56dpi.png", false, true, false),
                    Drinks("우롱티(iced)", Size(600, 0, 0), Caffeine(67, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_iced_oorong_tea_kvmfo7.png", false, true, false),
                    Drinks("레몬얼그레이티(hot)", Size(350, 0, 0), Caffeine(72, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_lemon_earlgraytea_hot_mmskpg.png", false, true, false),
                    Drinks("레몬얼그레이티(iced)", Size(600, 0, 0), Caffeine(72, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_lemon_earlgraytea_iced_md6rrx.png", false, true, false),
                    Drinks("오렌지자몽블랙티(hot)", Size(350, 0, 0), Caffeine(90, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_orange_grapefruit_blacktea_ieerwj.png", false, true, false),
                    Drinks("오렌지자몽블랙티(iced)", Size(600, 0, 0), Caffeine(90, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484620/paik/paik_iced_orange_grapefruit_black_tea_p4ntdc.png", false, true, false),
                    Drinks("빽사이즈 달콤아이스티(iced)", Size(950, 0, 0), Caffeine(32, 0, 0), "음료", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484616/paik/paik_paiksize_iced_tea_pegkmt.png", false, true, false),
                    Drinks("원조빽스치노(basic)", Size(625, 0, 0), Caffeine(327, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484617/paik/paik_basic_original_ppacschino_fjkvzb.png", false, true, false),
                    Drinks("원조빽스치노(soft)", Size(765, 0, 0), Caffeine(327, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484622/paik/paik_soft_original_ppacschino_xebyya.png", false, true, false),
                    Drinks("녹차빽스치노(basic)", Size(625, 0, 0), Caffeine(260, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_greentea_paiksccino_basic_rzbau8.png", false, true, false),
                    Drinks("녹차빽스치노(soft)", Size(765, 0, 0), Caffeine(260, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_greentea_paiksccino_soft_csf1rn.png", false, true, false),
                    Drinks("쿠키크런치빽스치노(basic)", Size(629, 0, 0), Caffeine(11, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484619/paik/paik_coockie_crunch_ppacschino_basic_oepp1v.png", false, true, false),
                    Drinks("쿠키크런치빽스치노(soft)", Size(749, 0, 0), Caffeine(11, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1662357433/paik/paik_coockie_crunch_ppacschino_soft_mesw5t.png", false, true, false),
                    Drinks("피스타치오빽스치노(basic)", Size(625, 0, 0), Caffeine(10, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484621/paik/paik_pistachio_ppacschino_basic_zvgqbp.png", false, true, false),
                    Drinks("피스타치오빽스치노(soft)", Size(765, 0, 0), Caffeine(10, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484616/paik/paik_pistachio_ppacschino_soft_zanjjv.png", false, true, false),
                    Drinks("퐁당치노(원조커피)", Size(592, 0, 0), Caffeine(255, 0, 0), "빽스치노", "빽다방", "https://res.cloudinary.com/cafeinbody/image/upload/v1660484618/paik/paik_pongdongchino_original_coffee_qfmgfv.png", false, true, false),)
                 }
        }

        fun addDrinksDatabaseTheVenti(db: DrinksDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                db.drinksDao().insertAll(
                    Drinks("아메리카노", Size(650, 0, 0), Caffeine(215, 0, 0), "커피", "더벤티", "282", true, true, false),
                    Drinks("(iced)아메리카노", Size(650, 0, 0), Caffeine(215, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602291/venti/venti_iced_americano_zadkr8.jpg", true, true, false),
                    Drinks("카페라떼(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602291/venti/venti_caffe_latte_ttsifv.jpg", true, true, false),
                    Drinks("바닐라딥라떼(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_vanila_dip_latte_kuswn3.jpg", true, true, false),
                    Drinks("바닐라크림콜드브루", Size(650, 0, 0), Caffeine(79, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602291/venti/venti_vanila_cream_cold_brew_dzwxto.jpg", true, true, false),
                    Drinks("디카페인바닐라크림콜드브루", Size(650, 0, 0), Caffeine(1, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_decaffeine_vanila_cream_cold_brew_u1xnxy.jpg", true, true, false),
                    Drinks("헤이즐넛딥라떼(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_hazelnut_dip_latte_em9g1w.jpg", true, true, false),
                    Drinks("헤이즐넛크림콜드브루", Size(650, 0, 0), Caffeine(79, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_hazelnut_cream_cold_brew_ghkhq8.jpg", true, true, false),
                    Drinks("디카페인헤이즐넛크림콜드브루", Size(650, 0, 0), Caffeine(1, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_decaffeine_hazelnut_cream_cold_brew_wisuky.jpg", true, true, false),
                    Drinks("더벤티사이즈아메리카노(32oz)", Size(1300, 0, 0), Caffeine(480, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_theventi_americano_mo7qlk.jpg", true, true, false),
                    Drinks("더벤티사이즈카페라떼(32oz)", Size(1300, 0, 0), Caffeine(528, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_theventi_caffe_latte_je0xso.jpg", true, true, false),
                    Drinks("더벤티사이즈믹스커피(32oz)", Size(1300, 0, 0), Caffeine(165, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_theventi_mixcoffee_fbb8ac.jpg", true, true, false),
                    Drinks("연유라떼(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_condensed_milk_latte_he0y94.jpg", true, true, false),
                    Drinks("카페모카(hot/ice)", Size(650, 0, 0), Caffeine(273, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_caffe_mocha_vsgaee.jpg", true, true, false),
                    Drinks("아인슈페너(hot/ice)", Size(325, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_einspener_dykmtu.jpg", true, true, false),
                    Drinks("꿀다크리카노(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602288/venti/venti_honey_darkricano_aijltc.jpg", true, true, false),
                    Drinks("바닐라 다크리카노(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_vanila_darkricano_pabrgo.jpg", true, true, false),
                    Drinks("헤이즐넛 다크리카노(hot/ice)", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_hazelnut_darkricano_hpqsjj.jpg", true, true, false),
                    Drinks("콜드브루", Size(650, 0, 0), Caffeine(92, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602291/venti/venti_cold_brew_vkipjt.jpg", true, true, false),
                    Drinks("콜드브루라떼", Size(650, 0, 0), Caffeine(92, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_cold_brew_latte_hiobxl.jpg", true, true, false),
                    Drinks("디카페인콜드브루", Size(650, 0, 0), Caffeine(1, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_decaffeine_cold_brew_kvnnoo.jpg", true, true, false),
                    Drinks("디카페인콜드브루라떼", Size(650, 0, 0), Caffeine(1, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_decaffeine_cold_brew_latte_c84ds3.jpg", true, true, false),
                    Drinks("믹스커피(hot/ice)", Size(650, 0, 0), Caffeine(110, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_mixcoffe_cfenui.jpg", true, true, false),
                    Drinks("토피넛라떼(hot/ice)", Size(650, 0, 0), Caffeine(132, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602291/venti/venti_toffeenut_latte_pxq2j3.jpg", true, true, false),
                    Drinks("카라멜마끼야또", Size(650, 0, 0), Caffeine(264, 0, 0), "커피", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_caramel_macchiato_d3xhrl.jpg", true, true, false),
                    Drinks("로얄밀크티", Size(650, 0, 0), Caffeine(75, 0, 0), "베버리지", "더벤티", "", false, true, false),
                    Drinks("녹차라떼(ice/hot)", Size(650, 0, 0), Caffeine(39, 0, 0), "베버리지", "더벤티", "", false, true, false),
                    Drinks("초코라떼(ice/hot)", Size(650, 0, 0), Caffeine(14, 0, 0), "베버리지", "더벤티", "", false, true, false),
                    Drinks("말차초코칩프라페", Size(650, 0, 0), Caffeine(214, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602288/venti/venti_matcha_chocochip_frappe_glbiuc.jpg", false, true, false),
                    Drinks("딸기초코칩프라페", Size(650, 0, 0), Caffeine(7, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602288/venti/venti_strawberry_chocochip_frappe_ioht3m.jpg", false, true, false),
                    Drinks("민트초코칩프라페", Size(650, 0, 0), Caffeine(18, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602288/venti/venti_mint_chocochip_frappe_yqdyol.jpg", false, true, false),
                    Drinks("토피넛초코칩프라페", Size(650, 0, 0), Caffeine(80, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_toffeenut_chocochip_frappe_g4r2wb.jpg", false, true, false),
                    Drinks("코코초코프라페", Size(650, 0, 0), Caffeine(28, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_coco_choco_frappe_bazrhq.jpg", false, true, false),
                    Drinks("자바칩프라페", Size(650, 0, 0), Caffeine(97, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602288/venti/venti_jaba_chip_frappe_dlzhrv.jpg", false, true, false),
                    Drinks("에스프레소쉐이크", Size(650, 0, 0), Caffeine(264, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_espresso_shake_n8w8gp.jpg", false, true, false),
                    Drinks("초코쉐이크", Size(650, 0, 0), Caffeine(14, 0, 0), "아이스블렌디드", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602287/venti/venti_choco_shake_w9rog9.jpg", false, true, false),
                    Drinks("로얄밀크티버블티", Size(650, 0, 0), Caffeine(75, 0, 0), "버블티/티", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_royal_milktea_bubble_tea_o6pcbn.jpg", false, true, false),
                    Drinks("복숭아아이스티", Size(650, 0, 0), Caffeine(20, 0, 0), "버블티/티", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602290/venti/venti_peach_iced_tea_fzouhd.jpg", false, true, false),
                    Drinks("더벤티사이즈복숭아아이스티(32oz)", Size(1300, 0, 0), Caffeine(30, 0, 0), "버블티/티", "더벤티", "https://res.cloudinary.com/cafeinbody/image/upload/v1659602289/venti/venti_theventi_peach_iced_tea_hptesr.jpg", false, true, false),)
            }
        }

        fun addDrinksDatabaseGongCha(db: DrinksDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                db.drinksDao().insertAll(
                    Drinks("(디카페인)헤이즐넛크림 콜드브루", Size(454, 624, 0), Caffeine(1, 0, 0), "디카페인", "공차", "", true, true, false),
                    Drinks("납작복숭아블루밍크러쉬", Size(454, 624, 0), Caffeine(17, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_flatpeach_blooming_crush_tbtyyb.png", false, true, false),
                    Drinks("납작복숭아쥬얼리밀크티", Size(454, 624, 0), Caffeine(21, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_flatpeach_jewerly_milktea_r64nqq.png", false, true, false),
                    Drinks("납작복숭아타르트크러쉬", Size(454, 624, 0), Caffeine(22, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_flatpeach_tart_crush_ihik1u.png", false, true, false),
                    Drinks("그릭요거트&밀크티크러쉬", Size(454, 624, 0), Caffeine(40, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_gric_milkteacrush_bvfipe.png", false, true, false),
                    Drinks("그릭요거트&망고밀크티크러쉬", Size(454, 624, 0), Caffeine(20, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_gric_mango_milkteacrush_tor4fj.png", false, true, false),
                    Drinks("그릭요거트&딸기밀크티크러쉬", Size(454, 624, 0), Caffeine(17, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_gric_strawaberry_milkteacrush_uskchm.png", false, true, false),
                    Drinks("리얼딸기쥬얼리밀크티", Size(454, 624, 0), Caffeine(19, 0, 0), "시즌메뉴", "공차", "", false, true, false),
                    Drinks("슈크림딸기밀크티", Size(454, 624, 0), Caffeine(55, 0, 0), "시즌메뉴", "공차", "", false, true, false),
                    Drinks("딸기초코쿠키스무디", Size(454, 624, 0), Caffeine(17, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_strawberry_choco_cookie_smoothie_tstpi0.png", false, true, false),
                    Drinks("딸기듬뿍밀크티", Size(454, 624, 0), Caffeine(24, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_strawberry_milktea_nbfkof.png", false, true, false),
                    Drinks("베리베리스무디", Size(454, 624, 0), Caffeine(29, 0, 0), "시즌메뉴", "공차", "", false, true, false),
                    Drinks("초당옥수수밀크티+펄", Size(454, 624, 0), Caffeine(24, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601509/gongcha/gongcha_corn_milktea_pearl_vfrzam.png", false, true, false),
                    Drinks("초당옥수수팝핑스무디", Size(454, 624, 0), Caffeine(28, 0, 0), "시즌메뉴", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_corn_poping_smoothie_q1ksh9.png", false, true, false),
                    Drinks("블랙밀크티+펄(ice)", Size(454, 624, 0), Caffeine(107, 104, 0), "베스트콤비네이션", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_black_milktea_pearl_tlgvn4.png", false, true, false),
                    Drinks("블랙밀크티+펄(hot)", Size(397, 454, 0), Caffeine(147, 118, 0), "베스트콤비네이션", "공차", "", false, true, false),
                    Drinks("피스타치오밀크티+펄(ice)", Size(454, 624, 0), Caffeine(20, 28, 0), "베스트콤비네이션", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_pistachio_milktea_pearl_ptjtti.png", false, true, false),
                    Drinks("피스타치오밀크티+펄(hot)", Size(397, 454, 0), Caffeine(21, 24, 0), "베스트콤비네이션", "공차", "", false, true, false),
                    Drinks("제주그린밀크티+펄(ice)", Size(454, 624, 0), Caffeine(49, 67, 0), "베스트콤비네이션", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_jeju_green_milktea_pearl_crybbs.png", false, true, false),
                    Drinks("제주그린티밀크티+펄(hot)", Size(397, 454, 0), Caffeine(62, 71, 0), "베스트콤비네이션", "공차", "", false, true, false),
                    Drinks("청포도그린티+알로에", Size(454, 624, 0), Caffeine(88, 122, 0), "베스트콤비네이션", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_grape_greentea_aloe_tchiqs.png", false, true, false),
                    Drinks("블랙티(ice)", Size(454, 624, 0), Caffeine(125, 126, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_blacktea_bxnrub.jpg", false, true, false),
                    Drinks("블랙티(hot)", Size(397, 454, 0), Caffeine(172, 144, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_blacktea_bxnrub.jpg", false, true, false),
                    Drinks("얼그레이티(ice)", Size(454, 624, 0), Caffeine(156, 145, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_earlgray_tea_v4xf6l.jpg", false, true, false),
                    Drinks("얼그레이티(hot)", Size(397, 454, 0), Caffeine(215, 166, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_earlgray_tea_v4xf6l.jpg", false, true, false),
                    Drinks("우롱티(ice)", Size(454, 624, 0), Caffeine(119, 112, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_oorong_tea_xwqcuv.jpg", false, true, false),
                    Drinks("우롱티(hot)", Size(397, 454, 0), Caffeine(164, 128, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_oorong_tea_xwqcuv.jpg", false, true, false),
                    Drinks("자스민그린티(ice)", Size(454, 624, 0), Caffeine(103, 103, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_jasmine_greentea_qg45v1.jpg", false, true, false),
                    Drinks("자스민그린티(hot)", Size(397, 454, 0), Caffeine(142, 118, 0), "오리지널티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_jasmine_greentea_qg45v1.jpg", false, true, false),
                    Drinks("블랙밀크티(ice)", Size(454, 624, 0), Caffeine(59, 81, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_black_milktea_ulejv8.jpg", false, true, false),
                    Drinks("블랙밀크티(hot)", Size(397, 454, 0), Caffeine(79, 91, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_black_milktea_ulejv8.jpg", false, true, false),
                    Drinks("얼그레이밀크티(ice)", Size(454, 624, 0), Caffeine(77, 106, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_earlgray_milktea_dgjwt4.jpg", false, true, false),
                    Drinks("얼그레이밀크티(hot)", Size(397, 454, 0), Caffeine(104, 118, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_earlgray_milktea_dgjwt4.jpg", false, true, false),
                    Drinks("우롱밀크티(ice)", Size(454, 624, 0), Caffeine(41, 56, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_oorong_milktea_ofpmnj.jpg", false, true, false),
                    Drinks("우롱밀크티(hot)", Size(397, 454, 0), Caffeine(45, 51, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_oorong_milktea_ofpmnj.jpg", false, true, false),
                    Drinks("자스민그린밀크티(ice)", Size(454, 624, 0), Caffeine(44, 60, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_jasmine_green_milktea_vm12ja.jpg", false, true, false),
                    Drinks("자스민그린밀크티(hot)", Size(397, 454, 0), Caffeine(59, 67, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_jasmine_green_milktea_vm12ja.jpg", false, true, false),
                    Drinks("초콜렛밀크티(ice)", Size(454, 624, 0), Caffeine(12, 17, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_choco_milktea_u3frt4.jpg", false, true, false),
                    Drinks("초콜렛밀크티(hot)", Size(397, 454, 0), Caffeine(16, 19, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_choco_milktea_u3frt4.jpg", false, true, false),
                    Drinks("피스타치오밀크티(ice)", Size(454, 624, 0), Caffeine(19, 26, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_pistachio_milktea_aflg5h.png", false, true, false),
                    Drinks("피스타치오밀크티(hot)", Size(397, 454, 0), Caffeine(25, 29, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_pistachio_milktea_aflg5h.png", false, true, false),
                    Drinks("제주그린밀크티(ice)", Size(454, 624, 0), Caffeine(49, 67, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_jeju_green_milktea_zuebup.png", false, true, false),
                    Drinks("제주그린밀크티(hot)", Size(397, 454, 0), Caffeine(62, 71, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_jeju_green_milktea_zuebup.png", false, true, false),
                    Drinks("하동호지밀크티(ice)", Size(454, 624, 0), Caffeine(167, 230, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_hadong_hoji_milktea_l5cuuf.png", false, true, false),
                    Drinks("하동호지밀크티(hot)", Size(397, 454, 0), Caffeine(172, 196, 0), "밀크티", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_hadong_hoji_milktea_l5cuuf.png", false, true, false),
                    Drinks("딸기쥬얼리밀크티", Size(454, 624, 0), Caffeine(13, 0, 0), "쥬얼리", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_strawberry_jewerly_milktea_nlffew.png", false, true, false),
                    Drinks("딸기쥬얼리요구르트크러쉬", Size(454, 624, 0), Caffeine(8, 0, 0), "쥬얼리", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_strawberry_jewerly_yogurt_crush_nnskji.png", false, true, false),
                    Drinks("청포도그린티+알로에", Size(454, 624, 0), Caffeine(58, 80, 0), "과일믹스", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601506/gongcha/gongcha_grape_greentea_hlrcd9.png", false, true, false),
                    Drinks("자몽그린티", Size(454, 624, 0), Caffeine(29, 39, 0), "과일믹스", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_grapefruit_greentea_mvhdqy.png", false, true, false),
                    Drinks("딸기쿠키스무디", Size(454, 624, 0), Caffeine(21, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_strawberry_cookie_smoothie_q5yvi2.png", false, true, false),
                    Drinks("제주그린티스무디", Size(454, 624, 0), Caffeine(88, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_jeju_greentea_smoothie_l3y5go.png", false, true, false),
                    Drinks("청포도스무디", Size(454, 624, 0), Caffeine(10, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_grape_smoothie_w3aqgr.png", false, true, false),
                    Drinks("초콜렛쿠키&크림스무디", Size(454, 624, 0), Caffeine(28, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601505/gongcha/gongcha_chococookie_cream_smoothie_jmjhjn.png", false, true, false),
                    Drinks("초코바른피스타치오스무디", Size(454, 624, 0), Caffeine(21, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_choco_pistachio_smoothie_ra9yo5.png", false, true, false),
                    Drinks("초코바른초코스무디", Size(454, 624, 0), Caffeine(15, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_choco_choco_smoothie_q4wwgs.png", false, true, false),
                    Drinks("초코바른제주그린스무디", Size(454, 624, 0), Caffeine(75, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_choco_jeju_green_smoothie_imfvml.png", false, true, false),
                    Drinks("돌체크러쉬위드샷", Size(454, 624, 0), Caffeine(109, 0, 0), "스무디", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601509/gongcha/gongcha_dolce_crush_with_shot_t6pnbe.png", false, true, false),
                    Drinks("아메리카노(ice)", Size(454, 624, 0), Caffeine(102, 185, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_americano_cpwjwo.jpg", true, true, false),
                    Drinks("아메리카노(hot)", Size(397, 454, 0), Caffeine(119, 209, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601508/gongcha/gongcha_americano_cpwjwo.jpg", true, true, false),
                    Drinks("카페라떼(ice)", Size(454, 624, 0), Caffeine(78, 199, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_caffe_latte_sfekwd.jpg", true, true, false),
                    Drinks("카페라떼(hot)", Size(397, 454, 0), Caffeine(109, 236, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_caffe_latte_sfekwd.jpg", true, true, false),
                    Drinks("돌체카페라떼(ice)", Size(454, 624, 0), Caffeine(93, 221, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_dolce_cafe_latte_xlte4v.png", true, true, false),
                    Drinks("돌체카페라떼(hot)", Size(397, 454, 0), Caffeine(145, 246, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_dolce_cafe_latte_xlte4v.png", true, true, false),
                    Drinks("카라멜카페라떼(ice)", Size(454, 624, 0), Caffeine(82, 215, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_caramel_caffe_latte_vzluxf.png", true, true, false),
                    Drinks("카라멜카페라떼(hot)", Size(397, 454, 0), Caffeine(131, 317, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601507/gongcha/gongcha_caramel_caffe_latte_vzluxf.png", true, true, false),
                    Drinks("바닐라카페라떼(ice)", Size(454, 624, 0), Caffeine(88, 219, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601509/gongcha/gongcha_vanila_caffe_latte_u24oli.png", true, true, false),
                    Drinks("바닐라카페라떼(hot)", Size(397, 454, 0), Caffeine(98, 249, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601509/gongcha/gongcha_vanila_caffe_latte_u24oli.png", true, true, false),
                    Drinks("모카카페라떼(ice)", Size(454, 624, 0), Caffeine(86, 219, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_mocha_caffe_latte_axia1f.png", true, true, false),
                    Drinks("모카카페라떼(hot)", Size(397, 454, 0), Caffeine(126, 249, 0), "커피", "공차", "https://res.cloudinary.com/cafeinbody/image/upload/v1659601504/gongcha/gongcha_mocha_caffe_latte_axia1f.png", true, true, false),)
            }
        }

    fun addStore(db: DrinksDatabase){
        CoroutineScope(Dispatchers.IO).launch {
            db.drinksDao().insertAll(
                Drinks("흑당카페라떼", Size(250, 0, 0), Caffeine(75,0, 0), "커피 및 커피우유", "푸르밀", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/purmil_blacksugar_latte_v2h3jk.png", false, false, false),
                Drinks("카페베네마리아바닐라라떼", Size(300, 0, 0), Caffeine(90,0, 0), "커피 및 커피우유", "푸르밀", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/purmil_caffebene_maria_vanilalatte_kbgjul.webp", false, false, false),
                Drinks("카페베네마리아스모키라떼", Size(300, 0, 0), Caffeine(160,0, 0), "커피 및 커피우유", "푸르밀", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/purmil_caffebene_maria_smockylatte_uablse.jpg", false, false, false),
                Drinks("아카페라카라멜마끼아또", Size(240, 0, 0), Caffeine(70,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391572/store/binggrae_caramel_macciato_raavzt.jpg", false, false, false),
                Drinks("아카페라바닐라라떼", Size(240, 0, 0), Caffeine(65,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_vanilalatte_tnn8ze.jpg", false, false, false),
                Drinks("아카페라사이즈업바닐라라떼", Size(350, 0, 0), Caffeine(90,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_sizeup_vanilalatte_brlnxq.jpg", false, false, false),
                Drinks("아카페라사이즈업아메리카노", Size(350, 0, 0), Caffeine(70,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391572/store/binggrae_sizeup_americano_kmf7ue.jpg", false, false, false),
                Drinks("아카페라사이즈업카페라떼", Size(350, 0, 0), Caffeine(90,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_sizeup_cafelatte_cc3dwq.jpg", false, false, false),
                Drinks("스페셜티토스티드카라멜마끼아또", Size(300, 0, 0), Caffeine(100,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_specialty_caramel_macciato_mzqvsr.jpg", false, false, false),
                Drinks("스페셜티멜로우바닐라라떼", Size(300, 0, 0), Caffeine(100,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_specialty_vanilalatte_baitsw.jpg", false, false, false),
                Drinks("스페셜티에티오피아예가체프", Size(460, 0, 0), Caffeine(190,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_specialty_yirgacheffe_pfq1eb.jpg", false, false, false),
                Drinks("스페셜티탄자니아킬리만자로", Size(460, 0, 0), Caffeine(200,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_specialty_kilimanjaro_hojgv0.jpg", false, false, false),
                Drinks("오늘의커피바닐라라떼", Size(250, 0, 0), Caffeine(65,0, 0), "커피 및 커피우유", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/binggrae_todaycoffee_vanilalatte_twdlwo.jpg", false, false, false),
                Drinks("카페라떼카라멜마끼야또", Size(220, 0, 0), Caffeine(100,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/maeil_cafelatte_caramelmacciato_zjimhi.jpg", false, false, false),
                Drinks("카페라떼마일드", Size(220, 0, 0), Caffeine(95,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/maeil_cafelatte_mild_wc0ceg.jpg", false, false, false),
                Drinks("카페라떼마일드로어슈거", Size(220, 0, 0), Caffeine(95,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/maeil_cafelatte_mild_lowersugar_uybvl8.jpg", false, false, false),
                Drinks("바리스타플라넬드립라떼", Size(325, 0, 0), Caffeine(145,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_flannel_driplatte_gzvjsq.jpg", false, false, false),
                Drinks("바리스타마다가스카르바닐라빈라떼", Size(325, 0, 0), Caffeine(105,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_madagascar_vanilabeanlatte_icoqwb.jpg", false, false, false),
                Drinks("바리스타콜드브루블랙", Size(325, 0, 0), Caffeine(165,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_coldbrew_black_ba19ol.jpg", false, false, false),
                Drinks("바리스타벨지엄쇼콜라모카", Size(325, 0, 0), Caffeine(125,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/maeil_barista_belgium_cholate_mocha_wkskci.jpg", false, false, false),
                Drinks("바리스타스모키로스팅라떼", Size(250, 0, 0), Caffeine(112,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/maeil_barista_smockieroastinglatte_efhnmu.jpg", false, false, false),
                Drinks("바리스타모카프레소", Size(250, 0, 0), Caffeine(95,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/maeil_barista_mochapresso_njdt2t.jpg", false, false, false),
                Drinks("바리스타카라멜딥프레소", Size(250, 0, 0), Caffeine(100,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_caramel_deeppresso_ntdsn8.jpg", false, false, false),
                Drinks("바리스타에스프레소라떼", Size(250, 0, 0), Caffeine(112,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/meail_barista_esprressolatte_a2qsbs.jpg", false, false, false),
                Drinks("바리스타로어슈거에스프레소라떼", Size(250, 0, 0), Caffeine(105,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_lowersugar_espressolatte_ggddrq.jpg", false, false, false),
                Drinks("바리스타그란데라떼", Size(475, 0, 0), Caffeine(150,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_grandelatte_wjgiyr.jpg", false, false, false),
                Drinks("바리스타그란데아메리카노", Size(475, 0, 0), Caffeine(190,0, 0), "커피 및 커피우유", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/maeil_barista_grande_americano_jozpvq.jpg", false, false, false),
                Drinks("프렌치카페에스프레소라떼", Size(250, 0, 0), Caffeine(110,0, 0), "커피 및 커피우유", "남양", "url", false, false, false),
                Drinks("프렌치카페옐로우비번돌체라떼", Size(250, 0, 0), Caffeine(105,0, 0), "커피 및 커피우유", "남양", "url", false, false, false),
                Drinks("프렌치카페블랙글레이즈드라떼", Size(250, 0, 0), Caffeine(95,0, 0), "커피 및 커피우유", "남양", "url", false, false, false),
                Drinks("프렌치카페로스터리킬링샷라떼", Size(500, 0, 0), Caffeine(130,0, 0), "커피 및 커피우유", "남양", "url", false, false, false),
                Drinks("스타벅스라떼", Size(200, 0, 0), Caffeine(92,0, 0), "커피 및 커피우유", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391577/store/seoul_starbucks_latte_nb6ylk.jpg", false, false, false),
                Drinks("강릉커피라떼", Size(250, 0, 0), Caffeine(142,0, 0), "커피 및 커피우유", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/seoul_gangneug_coffee_latte_z7ghpa.jpg", false, false, false),
                Drinks("스페셜티카페라떼마일드", Size(250, 0, 0), Caffeine(117,0, 0), "커피 및 커피우유", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391577/store/seoul_specialty_cafelatte_mild_ipre04.jpg", false, false, false),
                Drinks("스페셜티카페라떼모카", Size(250, 0, 0), Caffeine(110,0, 0), "커피 및 커피우유", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391577/store/seoul_specialty_cafelatte_mocha_mhtlwm.jpg", false, false, false),
                Drinks("커피빈아이스커피", Size(1000, 0, 0), Caffeine(606,0, 0), "커피 및 커피우유", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391576/store/seoul_coffebean_ice_coffee_fshlao.jpg", false, false, false),
                Drinks("오리진돌체라떼", Size(250, 0, 0), Caffeine(120,0, 0), "커피 및 커피우유", "덴마크", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/denmark_origin_dolcelatte_a2t37u.jpg", false, false, false),
                Drinks("오리진카페라떼", Size(250, 0, 0), Caffeine(130,0, 0), "커피 및 커피우유", "덴마크", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/denmark_origin_caffelatte_ptlmac.jpg", false, false, false),
                Drinks("프라푸치노모카", Size(281, 0, 0), Caffeine(82,0, 0), "커피 및 커피우유", "스타벅스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391577/store/starbucks_frappuchino_mocha_gpcdbv.jpg", false, false, false),
                Drinks("레쓰비", Size(175, 0, 0), Caffeine(74,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/lotte_letsbe_vxpp6h.jpg", false, false, false),
                Drinks("레쓰비그란데아메리카노", Size(500, 0, 0), Caffeine(204,0, 0), "커피 및 커피우유", "롯데칠성", "url", false, false, false),
                Drinks("레쓰비그란데라떼", Size(500, 0, 0), Caffeine(190,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391575/store/lotte_letsbe_grandelatte_ulog88.jpg", false, false, false),
                Drinks("칸타타콘트라베이스블랙", Size(500, 0, 0), Caffeine(179,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_cantata_contrabase_black_fh99cv.jpg", false, false, false),
                Drinks("칸타타콘트라베이스콜드브루라떼", Size(500, 0, 0), Caffeine(165,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_contrabase_coldbrew_latte_qgtrqf.jpg", false, false, false),
                Drinks("칸타타프리미엄라떼", Size(275, 0, 0), Caffeine(156,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_cantata_premiumlatte_hyd7ot.jpg", false, false, false),
                Drinks("칸타타스위트아메리카노", Size(275, 0, 0), Caffeine(112,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_cantata_sweet_americano_qtqbgr.jpg", false, false, false),
                Drinks("칸타타라떼홀릭바닐라라떼", Size(240, 0, 0), Caffeine(95,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_cantata_latteholic_vanilalatte_ursfn9.jpg", false, false, false),
                Drinks("칸타타라떼홀릭카페라떼", Size(240, 0, 0), Caffeine(138,0, 0), "커피 및 커피우유", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391574/store/lotte_cantata_latteholic_cafelatte_weys02.jpg", false, false, false),
                Drinks("조지아고티카빈티지라떼", Size(270, 0, 0), Caffeine(109,0, 0), "커피 및 커피우유", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/coca_georgia_cotika_vintagelatte_ldkxc0.jpg", false, false, false),
                Drinks("조지아크래프트블랙", Size(470, 0, 0), Caffeine(205,0, 0), "커피 및 커피우유", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662391573/store/coca_georgia_craftblack_oa4khu.jpg", false, false, false),
                Drinks("조지아크래프트카페라떼", Size(470, 0, 0), Caffeine(229,0, 0), "커피 및 커피우유", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/coca_georgia_craftcafelatte_n59px3.jpg", false, false, false),
                Drinks("조지아라떼니스타크리미라떼", Size(280, 0, 0), Caffeine(110,0, 0), "커피 및 커피우유", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/coca_georgia_lattenistar_creamylatte_lofqni.webp", false, false, false),
                Drinks("조지아라떼니스타카라멜라떼", Size(280, 0, 0), Caffeine(130,0, 0), "커피 및 커피우유", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/coca_georgia_lattenistar_caramellatte_zz8ofh.webp", false, false, false),
                Drinks("맥스웰콜롬비아스위트블랙", Size(500, 0, 0), Caffeine(157,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxwell_colombiasweetblack_yiyplj.jpg", false, false, false),
                Drinks("맥스웰하우스마스터블랙", Size(500, 0, 0), Caffeine(157,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/dongseo_maxwell_masterblack_r6hsqe.jpg", false, false, false),
                Drinks("맥심TOP스위트아메리카노", Size(275, 0, 0), Caffeine(96,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxim_top_sweetamericano_d5dymz.jpg", false, false, false),
                Drinks("맥심TOP더블랙", Size(275, 0, 0), Caffeine(96,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxim_top_theblack_dpsnkm.jpg", false, false, false),
                Drinks("맥심TOP마스터라떼", Size(275, 0, 0), Caffeine(83,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxim_top_masterlatte_fxg6g1.jpg", false, false, false),
                Drinks("맥심TOP심플리스무스스위트아메리카노", Size(240, 0, 0), Caffeine(98,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxim_top_simplysmooth_sweetamericano_punbhk.jpg", false, false, false),
                Drinks("맥심TOP심플리스무스블랙", Size(240, 0, 0), Caffeine(98,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_maxim_top_simplysmooth_black_xhnuwj.jpg", false, false, false),
                Drinks("맥심TOP심플리스무스라떼", Size(240, 0, 0), Caffeine(112,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464594/store/dongseo_maxim_top_simplysmooth_latte_dewm3j.jpg", false, false, false),
                Drinks("스타벅스스키니라떼", Size(270, 0, 0), Caffeine(139,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_starbucks_skinnylatte_fm0iwt.jpg", false, false, false),
                Drinks("스타벅스카페라떼", Size(270, 0, 0), Caffeine(124,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464595/store/dongseo_starbucks_caffelatte_270_n5ygsc.png", false, false, false),
                Drinks("스타벅스카페라떼", Size(200, 0, 0), Caffeine(92,0, 0), "커피 및 커피우유", "동서", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/dongseo_starbucks_caffelatte_200_ekitpj.jpg", false, false, false),
                Drinks("시그니처스위트아메리카노", Size(390, 0, 0), Caffeine(150,0, 0), "커피 및 커피우유", "홈플러스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/homeplus_signature_sweetamericano_j6m6yn.jpg", false, false, false),
                Drinks("시그니처카페라떼", Size(390, 0, 0), Caffeine(142,0, 0), "커피 및 커피우유", "홈플러스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/homeplus_signature_caffelatte_qhzry7.webp", false, false, false),
                Drinks("시그니처블랙", Size(390, 0, 0), Caffeine(136,0, 0), "커피 및 커피우유", "홈플러스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/homeplus_signature_black_uhwi17.png", false, false, false),
                Drinks("카페라떼", Size(300, 0, 0), Caffeine(280,0, 0), "커피 및 커피우유", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/ediya_caffelatte_nes3d5.jpg", false, false, false),
                Drinks("토피넛시그니처라떼", Size(300, 0, 0), Caffeine(210,0, 0), "커피 및 커피우유", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/ediya_toffeenutsignaturelatte_rw8tlb.jpg", false, false, false),
                Drinks("돌체콜드브루", Size(300, 0, 0), Caffeine(230,0, 0), "커피 및 커피우유", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/ediya_dolcecoldbrew_re0e79.jpg", false, false, false),
                Drinks("바닐라라떼", Size(300, 0, 0), Caffeine(220,0, 0), "커피 및 커피우유", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/ediya_vanillalatte_k9vwwu.webp", false, false, false),
                Drinks("쇼콜라모카", Size(300, 0, 0), Caffeine(225,0, 0), "커피 및 커피우유", "이디야", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/ediya_chocolatmocha_g42del.jpg", false, false, false),
                Drinks("85도씨소금커피", Size(300, 0, 0), Caffeine(96,0, 0), "커피 및 커피우유", "대만", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/taiwan_85celsiusdegree_saltcoffee_e94fzh.png", false, false, false),
                Drinks("오리진돌체라떼", Size(250, 0, 0), Caffeine(120,0, 0), "커피 및 커피우유", "동원", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464598/store/dongwon_origin_dolcelatte_sixdac.jpg", false, false, false),
                Drinks("오리진카페라떼", Size(250, 0, 0), Caffeine(130,0, 0), "커피 및 커피우유", "동원", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/dongwon_origin_caffelatte_sxrvec.jpg", false, false, false),
                Drinks("오리진바닐라라떼", Size(250, 0, 0), Caffeine(170,0, 0), "커피 및 커피우유", "동원", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464597/store/dongwon_origin_vanillalatte_zbxkje.jpg", false, false, false),
                Drinks("오리진모카라떼", Size(250, 0, 0), Caffeine(150,0, 0), "커피 및 커피우유", "동원", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/dongwon_origin_mochalatte_rw1gmm.jpg", false, false, false),
                Drinks("오리진아메리카노", Size(250, 0, 0), Caffeine(180,0, 0), "커피 및 커피우유", "동원", "https://res.cloudinary.com/cafeinbody/image/upload/v1662476003/store/dongwon_origin_americano_fgunn6.jpg", false, false, false),
                Drinks("커피아이스아메리카노", Size(1000, 0, 0), Caffeine(510,0, 0), "커피 및 커피우유", "씨유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/cu_coffee_iceamericano_qbu5ny.jpg", false, false, false),
                Drinks("커피믹스커피", Size(1000, 0, 0), Caffeine(380,0, 0), "커피 및 커피우유", "씨유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/cu_coffee_mixcoffee_aj0rf2.jpg", false, false, false),
                Drinks("커피", Size(500, 0, 0), Caffeine(130,0, 0), "커피 및 커피우유", "씨유", "url", false, false, false),
                Drinks("폴바셋돌체라떼", Size(330, 0, 0), Caffeine(185,0, 0), "커피 및 커피우유", "엠즈", "https://res.cloudinary.com/cafeinbody/image/upload/v1662464596/store/mz_paulbasset_dolcelatte_jxs635.webp", false, false, false),
                Drinks("핫식스(오리지널, 자몽)", Size(250, 0, 0), Caffeine(60,0, 0), "에너지드링크", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/lotte_hot6_original_250_gnp7bt.jpg", false, false, false),
                Drinks("핫식스(오리지널,더킹펀치, 더킹포스, 더킹파워, 더킹스톰, 더킹러쉬)", Size(355, 0, 0), Caffeine(86,0, 0), "에너지드링크", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/lotte_hot6_original_355_kqvx0s.jpg", false, false, false),
                Drinks("핫식스 바이탈 에너지/무브업 에너지", Size(500, 0, 0), Caffeine(120,0, 0), "에너지드링크", "롯데칠성", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/lotte_hot6_vital_energy_ksjopm.jpg", false, false, false),
                Drinks("레드불", Size(250, 0, 0), Caffeine(63,0, 0), "에너지드링크", "유한회사", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/yuhan_redbull_drnkla.webp", false, false, false),
                Drinks("몬스터(울트라/그린/시트라/펀치/파라다이스/망고)", Size(355, 0, 0), Caffeine(100,0, 0), "에너지드링크", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662579155/store/coca_monster_ym2jui.jpg", false, false, false),
                Drinks("말표 (다크홀스/화이트)", Size(250, 0, 0), Caffeine(100,0, 0), "에너지드링크", "말표산업", "https://res.cloudinary.com/cafeinbody/image/upload/v1662579155/store/malpyo_malpyo_a47yrn.jpg", false, false, false),

                Drinks("타브카(오리지널)", Size(355, 0, 0), Caffeine(104,0, 0), "에너지드링크", "오케이에프음료", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/okef_tabca_original_llo0ea.jpg", false, false, false),
                Drinks("타브카(버스트)", Size(355, 0, 0), Caffeine(103,0, 0), "에너지드링크", "오케이에프음료", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578945/store/okef_tabca_burst_wqc0kj.jpg", false, false, false),
                Drinks("에너지티", Size(5, 0, 0), Caffeine(70,0, 0), "에너지드링크", "티젠", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558059/yj/4085018_1_qfrl14.webp", false, false, false),
                Drinks("백셀 에너지 드링크", Size(250, 0, 0), Caffeine(60,0, 0), "에너지드링크", "동화약품", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558147/yj/B_ikwkwl.jpg", false, false, false),
                Drinks("지파크", Size(250, 0, 0), Caffeine(60,0, 0), "에너지드링크", "동화약품", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558214/yj/5929369_1_qi1xsy.webp", false, false, false),
                Drinks("빡텐션", Size(13, 0, 0), Caffeine(175,0, 0), "에너지드링크", "희망그린식품", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558303/yj/207f4d845f3d4b80b237d662d8aad89b_jexsqo.jpg", false, false, false),
                Drinks("야[약국용]", Size(100, 0, 0), Caffeine(150,0, 0), "에너지드링크", "삼성제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662632349/yj/ya_u1qwz8.jpg", false, false, false),
                Drinks("야[편의점]", Size(250, 0, 0), Caffeine(150,0, 0), "에너지드링크", "삼성제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662632349/yj/ya_u1qwz8.jpg", false, false, false),



                Drinks("닥터페퍼", Size(354, 0, 0), Caffeine(41,0, 0), "콜라", "코카콜라", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578304/store/coca_drpepper_ate5rf.jpg", false, false, false),
                Drinks("김치 에너지 음료", Size(240, 0, 0), Caffeine(70,0, 0), "에너지드링크", "금강B&F", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578304/store/keumkang_kimchi_energydrink_jgjpqn.jpg", false, false, false),
                Drinks("박카스f/d", Size(120, 0, 0), Caffeine(30,0, 0), "자양강장제", "동아제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662578303/store/donga_bacchus_e7ui7p.webp", false, false, false),
                Drinks("구론산 바몬드/스파클링", Size(150, 0, 0), Caffeine(30,0, 0), "자양강장제", "해태htb", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558871/yj/4007521_1_tbxdb2.jpg", false, false, false),
                Drinks("오로나민C", Size(120, 0, 0), Caffeine(14,0, 0), "자양강장제", "동아오츠카", "https://res.cloudinary.com/cafeinbody/image/upload/v1662558823/yj/215025305915_rb8zaw.jpg", false, false, false),
                Drinks("그린티", Size(100, 0, 0), Caffeine(100,0, 0), "아이스크림", "하겐다즈", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557949/yj/4085018_1_nmjdva.webp", false, false, false),
                Drinks("그린티", Size(100, 0, 0), Caffeine(73,0, 0), "아이스크림", "배스킨라빈스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557898/yj/green_tea_wkvdxf.jpg", false, false, false),
                Drinks("끌레도르 심플리 퓨어 그린티", Size(100, 0, 0), Caffeine(59,0, 0), "아이스크림", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557695/yj/5112333_1_nfvawz.webp", false, false, false),
                Drinks("나뚜루 그린티비너스", Size(100, 0, 0), Caffeine(59,0, 0), "아이스크림", "롯데제과", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557651/yj/5364475_1_kn1wb0.jpg", false, false, false),
                Drinks("달고나커피우유", Size(500, 0, 0), Caffeine(37,0, 0), "커피우유 및 기타음료", "연세대학교", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557525/yj/6d919773-b59c-4225-99d0-bddc29f48299_rsd35h.jpg", false, false, false),
                Drinks("스누피 더진한커피우유", Size(500, 0, 0), Caffeine(237,0, 0), "커피우유 및 기타음료", "GS25", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557481/yj/1d0833bad8a906ea37d6efb3e98e6684_1462888488_9455_kozug3.jpg", false, false, false),
                Drinks("커피맛우유", Size(240, 0, 0), Caffeine(85,0, 0), "커피우유 및 기타음료", "빙그레", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557401/yj/720X720_nbonwp.jpg", false, false, false),
                Drinks("커피에몽", Size(250, 0, 0), Caffeine(85,0, 0), "커피우유 및 기타음료", "남양", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557349/yj/4A0DAAB83AE64AD8A8975CC5B75963AB_kzwy00.jpg", false, false, false),
                Drinks("우유속에카페돌체", Size(310, 0, 0), Caffeine(88,0, 0), "커피우유 및 기타음료", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557296/yj/4121175_1_hh4qws.jpg", false, false, false),
                Drinks("우유속에모카치노", Size(310, 0, 0), Caffeine(82,0, 0), "커피우유 및 기타음료", "매일", "https://res.cloudinary.com/cafeinbody/image/upload/v1662557200/yj/600_4_otw8ft.jpg", false, false, false),
                Drinks("커피포리200", Size(200, 0, 0), Caffeine(45,0, 0), "커피우유 및 기타음료", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662556131/yj/B_qeqjwq.jpg", false, false, false),
                Drinks("서울우유커피맛", Size(200, 0, 0), Caffeine(42,0, 0), "커피우유 및 기타음료", "서울우유", "https://res.cloudinary.com/cafeinbody/image/upload/v1662556054/yj/10000000000354_GOODS_1.jpeg_qze7xt.jpg", false, false, false),
                Drinks("HEYROO커피우유", Size(500, 0, 0), Caffeine(110,0, 0), "커피우유 및 기타음료", "CU", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555975/yj/8801121760345_ktj5hc.jpg", false, false, false),
                Drinks("덴마크커피커피우유", Size(300, 0, 0), Caffeine(60,0, 0), "커피우유 및 기타음료", "덴마크", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555911/yj/8801155731847_kghvn7.jpg", false, false, false),
                Drinks("홍루이젠로얄밀크티퀸", Size(250, 0, 0), Caffeine(96,0, 0), "커피우유 및 기타음료", "서강", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555789/yj/1000265684819_i1_1200_jzjw1h.jpg", false, false, false),
                Drinks("홍루이젠로얄밀크티휴", Size(250, 0, 0), Caffeine(59,0, 0), "커피우유 및 기타음료", "서강", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555742/yj/1000048643339_i1_1200_eeqkso.jpg", false, false, false),
                Drinks("가나프리미어마일드", Size(70, 0, 0), Caffeine(8,0, 0), "초콜릿", "롯데", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555620/yj/8801062009824_zelnfd.jpg", false, false, false),
                Drinks("72%드림카카오", Size(86, 0, 0), Caffeine(36,0, 0), "초콜릿", "롯데", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555528/yj/600_3_yk5ned.jpg", false, false, false),
                Drinks("태블릿밀크멕시코", Size(79, 0, 0), Caffeine(12,0, 0), "초콜릿", "고디바", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555386/yj/5614909_1_sh0iy6.jpg", false, false, false),
                Drinks("태블릿다크멕시코", Size(79, 0, 0), Caffeine(17,0, 0), "초콜릿", "고디바", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555317/yj/5614858_1_ymxcil.jpg", false, false, false),
                Drinks("밀크초콜릿", Size(100, 0, 0), Caffeine(10,0, 0), "초콜릿", "노브랜드", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555189/yj/10959414872.20170123092449_nq8aao.jpg", false, false, false),
                Drinks("다크초콜릿", Size(100, 0, 0), Caffeine(11,0, 0), "초콜릿", "노브랜드", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555125/yj/1000015128753_i1_1200_oercos.jpg", false, false, false),
                Drinks("밀크초콜릿", Size(43, 0, 0), Caffeine(4,0, 0), "초콜릿", "도브", "https://res.cloudinary.com/cafeinbody/image/upload/v1662555000/yj/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-09-07_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.48.39_bllqpn.png", false, false, false),
                Drinks("스포트알파인밀크초콜릿", Size(100, 0, 0), Caffeine(8,0, 0), "초콜릿", "리터", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554841/yj/600_2_sflfcq.jpg", false, false, false),
                Drinks("알프스밀크", Size(100, 0, 0), Caffeine(8,0, 0), "초콜릿", "밀카", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554752/yj/2825928691_B_yofgyy.jpg", false, false, false),
                Drinks("심플러스벨지안밀크초콜릿", Size(100, 0, 0), Caffeine(14,0, 0), "초콜릿", "홈플러스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554640/yj/6039625_1_irotbp.jpg", false, false, false),
                Drinks("심플러스벨지안다크초콜릿", Size(100, 0, 0), Caffeine(16,0, 0), "초콜릿", "홈플러스", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554578/yj/6039643_1_m17pkn.jpg", false, false, false),
                Drinks("온리프라이스벨기에밀크초콜릿", Size(100, 0, 0), Caffeine(13,0, 0), "초콜릿", "벨지안초콜릿그룹", "블로그사진", false, false, false),
                Drinks("온리프라이스벨기에다크초콜릿", Size(100, 0, 0), Caffeine(25,0, 0), "초콜릿", "벨지안초콜릿그룹", "블로그사진", false, false, false),
                Drinks("데어리밀크초콜릿", Size(165, 0, 0), Caffeine(17,0, 0), "초콜릿", "캐드버리", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554419/yj/B_xee6z1.jpg", false, false, false),
                Drinks("스위스밀크초콜릿", Size(100, 0, 0), Caffeine(12,0, 0), "초콜릿", "토블론", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554308/yj/2925465441_B_zqd4ow.jpg", false, false, false),
                Drinks("스위스다크초콜릿", Size(100, 0, 0), Caffeine(20,0, 0), "초콜릿", "토블론", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554206/yj/600_1_izvg8k.jpg", false, false, false),
                Drinks("밀크초콜릿자이언트바", Size(198, 0, 0), Caffeine(31,0, 0), "초콜릿", "허쉬", "https://res.cloudinary.com/cafeinbody/image/upload/v1662554049/yj/124740452_1_c9fnhr.jpg", false, false, false),
                Drinks("인텐스다크86%카카오", Size(117, 0, 0), Caffeine(20,0, 0), "초콜릿", "기라델리", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553943/yj/122970545_1_y5youh.jpg", false, false, false),
                Drinks("다크초콜릿", Size(100, 0, 0), Caffeine(44,0, 0), "초콜릿", "까쉐우간다", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553834/yj/600_xhycz1.jpg", false, false, false),
                Drinks("엑셀런트다크90%", Size(100, 0, 0), Caffeine(18,0, 0), "초콜릿", "린트", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553647/yj/15979167167.20181105150116_ta5osj.jpg", false, false, false),
                Drinks("마켓오초콜릿오리지널", Size(36, 0, 0), Caffeine(9,0, 0), "초콜릿", "오리온", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553553/yj/4284214851_B_behycq.jpg", false, false, false),
                Drinks("파인다크초콜릿코코아85%", Size(100, 0, 0), Caffeine(22,0, 0), "초콜릿", "바인리히", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553484/yj/29783b0736_w57bau.jpg", false, false, false),
                Drinks("시모아다크초콜릿", Size(80, 0, 0), Caffeine(48,0, 0), "초콜릿", "시모아", "https://res.cloudinary.com/cafeinbody/image/upload/v1662553267/yj/4782221869_B_hgjyv2.jpg", false, false, false),
                Drinks("약콩초콜릿", Size(40, 0, 0), Caffeine(12,0, 0), "초콜릿", "밥스누(서울대학교)", "https://res.cloudinary.com/cafeinbody/image/upload/v1662552826/yj/1000469355891_i1_1200_jpgxre.jpg", false, false, false),
            )}}

    fun addEtc(db: DrinksDatabase){
        CoroutineScope(Dispatchers.IO).launch {
            db.drinksDao().insertAll(
                Drinks("게보린정", Size(0, 0, 0), Caffeine(50,0, 0), "해열·진통제", "삼진제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662634431/etc/gaebolin_cenjv9.jpg", false, false, false),
                Drinks("펜잘큐정", Size(0, 0, 0), Caffeine(50,0, 0), "해열·진통제", "종근당", "https://res.cloudinary.com/cafeinbody/image/upload/v1662634430/etc/panpilin_khmwjs.jpg", false, false, false),
                Drinks("그날엔정", Size(0, 0, 0), Caffeine(40,0, 0), "해열·진통제", "정동제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662634430/etc/guenalan_nwvdpo.jpg", false, false, false),
                Drinks("판피린큐액", Size(20, 0, 0), Caffeine(30,0, 0), "해열·진통제", "동아제약", "https://res.cloudinary.com/cafeinbody/image/upload/v1662634430/etc/guenalan_nwvdpo.jpg", false, false, false),
                Drinks("판콜에스내복액", Size(0, 0, 0), Caffeine(30,0, 0), "해열·진통제", "동화약품", "https://res.cloudinary.com/cafeinbody/image/upload/v1662634431/etc/pancols_e4fdtb.jpg", false, false, false),)
        }
    }

    fun addNonCaf(db: DrinksDatabase){
        CoroutineScope(Dispatchers.IO).launch {
            db.drinksDao().insertAll(
                Drinks("캐모마일티", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662309508/noncaffeine/camomile_rzepdz.webp", false, false, false),
                Drinks("페퍼민트티", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662309507/noncaffeine/pepperminttea_wfxexp.jpg", false, false, false),
                Drinks("루이보스티", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662311791/noncaffeine/rouibosetea_acbwqd_da1517.jpg", false, false, false),
                Drinks("대추차", Size(0, 0, 0), Caffeine(0,0, 0), "차","논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662311903/noncaffeine/daechootea_g5hsga.jpg", false, false, false),
                Drinks("생강차", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662309507/noncaffeine/gingertea_qordvu.jpg", false, false, false),
                Drinks("쌍화차", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662309508/noncaffeine/ssanghwatea_hzxmus.jpg", false, false, false),
                Drinks("오곡라떼", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662309507/noncaffeine/grainlatte_omgsu5.jpg", false, false, false),
                Drinks("고구마라떼", Size(0, 0, 0), Caffeine(0,0, 0), "차", "논카페인", "https://res.cloudinary.com/cafeinbody/image/upload/v1662312215/noncaffeine/sweetpotatolatte_fiyqz3.png", false, false, false),

                )
        }
    }
    }