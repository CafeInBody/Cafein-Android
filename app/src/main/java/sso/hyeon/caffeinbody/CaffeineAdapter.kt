package sso.hyeon.caffeinbody

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import sso.hyeon.caffeinbody.database.Drinks
import sso.hyeon.caffeinbody.database.DrinksDatabase
import kotlinx.coroutines.*


class CaffeineAdapter (private val context: Context, type:CaffeinCase) : RecyclerView.Adapter<CaffeineAdapter.ViewHolder>() {
    var type: CaffeinCase? = type
    var datas = mutableListOf<Drinks>()
    var style: Int? = null

    var home = false
    var madeby = false
    var recommend = true

    lateinit var owner: ViewModelStoreOwner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(type== CaffeinCase.SMALL) style = R.layout.item_rec_caffeine
        else if(type == CaffeinCase.LARGE) style = R.layout.item_caffeine

        val view = LayoutInflater.from(context).inflate(style!!,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article : Drinks = datas.get(position)//한 번에 6개씩 가져오는거였어
        holder.bind(article)

        holder.star.setOnClickListener{
            Log.e("CaffeineAdapter2 sorted?", datas.toString())
            CoroutineScope(Dispatchers.IO).launch {var int = 0
                var db = DrinksDatabase.getInstance(context)!!// 를 반환
                db!!.drinksDao().updateFavorite(!article.favorite, article.id)
                /*CoroutineScope(Main).launch {
                    val viewModel = ViewModelProvider(owner).get(CaffeineViewModel::class.java)
                    val iscafe = article.iscafe
                    val madefrom = article.madeBy
                    if (home == true){
                        val result2 = viewModel.getAll2(iscafe, this@CaffeineAdapter)
                    }else if (madeby == true){
                        val result = viewModel.getFilteredMadeby2(iscafe, madefrom, this@CaffeineAdapter)
                    }
                }*/
            }
            //notifyDataSetChanged()


        }


        holder.itemView.setOnClickListener{
            if(article.madeBy != "논카페인") {
                val intent = Intent(holder.itemView.context, DrinkCaffeineActivity::class.java)
                //서버있으면지워도됨
                intent.putExtra("name", article.id)
                intent.putExtra("star", article.favorite)

                ContextCompat.startActivity(holder.itemView.context, intent, null)
                //카페인 마시기로 이동}
        }}
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txt: TextView = itemView.findViewById(R.id.txt_name)
        private val img: ImageView = itemView.findViewById(R.id.img_caffeine)
        private val logo: ImageView = itemView.findViewById(R.id.logo)
     //   private val caf: TextView = itemView.findViewById(R.id.txt_caffeine)
        private val caf: TextView = itemView.findViewById(R.id.caf_name)
        private val drink: TextView = itemView.findViewById(R.id.txt_caffeine)
        val star: ToggleButton = itemView.findViewById(R.id.star)

        private val cafcard: CardView = itemView.findViewById(R.id.caffeine)

        fun bind(article: Drinks){
           // txt.text = article.drinkName
          //  caf.text = article.caffeine?.caffeine1.toString() +"mg"

           // Log.e("CaffeineAdapter", "binding ")
            txt.text = article.madeBy
            drink.text = article.drinkName
            if(article.caffeine?.caffeine1 !=0 )  caf.text= article.caffeine?.caffeine1.toString() +"mg"
            else caf.text= article.caffeine?.caffeine2.toString() +"mg"

            star.isChecked = article.favorite == true



            var cacheDir = context.cacheDir
            if(article.madeBy == "논카페인" ){
                star.visibility = GONE
                cafcard.visibility = GONE
            }

            if (article.imgurl.startsWith("https:")) {
                img.setImageResource(0)
                //Log.e("오류해결", article.drinkName + " " + article.madeBy + article.imgurl)
                Glide.with(itemView)
                    .load(article.imgurl).override(450)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_type)
                    .into(img)
                img.visibility = VISIBLE
                logo.visibility = GONE

            } else if(article.imgurl.startsWith("$cacheDir/")){
                img.setImageResource(0)
                //Log.e("CaffeineAdapter", "2" + article.toString())
                val bm = BitmapFactory.decodeFile(article.imgurl)
                Glide.with(itemView).load(bm)
                    .override(600).into(img)
                img.visibility = VISIBLE
                logo.visibility = GONE
            }
            else {
                img.setImageResource(0)
                //Log.e("CaffeineAdapter", "3" + article.toString())
                when(article.madeBy){
                    "스타벅스"->{Glide.with(itemView)
                        .load(R.drawable.starbucks_logo).override(250).into(logo)}
                        //Log.e("오류해결", "스 " + article.drinkName + article.madeBy)}
                    "할리스"->{Glide.with(itemView)
                        .load(R.drawable.hollys_logo).override(250).into(logo)}
                        //Log.e("오류해결", "할 " + article.drinkName + article.madeBy)}
                    "투썸플레이스"->{Glide.with(itemView)
                        .load(R.drawable.twosome_logo).override(250).into(logo)}
                        //Log.e("오류해결", "투 " + article.drinkName + article.madeBy)}
                    "이디야" -> { Glide.with(itemView)
                        .load(R.drawable.ediya_logo).override(250).into(logo)}
                        //Log.e("오류해결", "이 " + article.drinkName + article.madeBy)}
                    "빽다방"-> {Glide.with(itemView)
                        .load(R.drawable.paiks_logo).override(250).into(logo)}
                        //Log.e("오류해결", "빽 " + article.drinkName + article.madeBy)}
                    "더벤티" -> {Glide.with(itemView)
                        .load(R.drawable.theventi_logo).override(250).into(logo)}
                        //Log.e("오류해결", "더 " + article.drinkName + article.madeBy)}

                    "공차"->{Glide.with(itemView)
                        .load(R.drawable.gongcha_logo).override(250).into(logo)}
                        //Log.e("오류해결", "공 " + article.drinkName + article.madeBy)}
                    else->{Glide.with(itemView).load(R.drawable.logo_type).into(logo)
                        Log.e("오류해결", "그외 " + article.drinkName + article.madeBy)}


                }
                img.visibility = GONE
                logo.visibility = VISIBLE
            }




        }

    }

    /*fun sortDb(madeby: String){
        val db = DrinksDatabase.getInstance(context)!!

        CoroutineScope(Dispatchers.Main).launch {
            val makers = CoroutineScope(Dispatchers.IO).async {
                db.drinksDao().selectDrinkMadeBy(madeby)
            }.await()

            for (maker in makers){
                Log.e("maker", "selectDrinkName: " + maker)
                //   datas.add(CaffeineData(1, maker.id, maker.drinkName, 0) )
                if(maker.iscafe) datas.add(maker)

            }
            Log.e("ba", datas[0].madeBy)

            datas.clear()
            datas.addAll(datas)
            Log.e("sorted", datas.toString())
            notifyDataSetChanged()
        }*/


}