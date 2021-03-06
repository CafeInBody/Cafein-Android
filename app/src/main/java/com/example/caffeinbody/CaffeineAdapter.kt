package com.example.caffeinbody

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.caffeinbody.database.Drinks

class CaffeineAdapter (private val context: Context) : RecyclerView.Adapter<CaffeineAdapter.ViewHolder>() {

    var type: CaffeinCase? = null
    var datas = mutableListOf<Drinks>()
    var style: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(type== CaffeinCase.SMALL) style = R.layout.item_caffeine
        else style = R.layout.item_caffeine

        val view = LayoutInflater.from(context).inflate(style!!,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article : Drinks = datas.get(position)
        holder.bind(article)


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DrinkCaffeineActivity::class.java)
            intent.putExtra("id",position)

            //서버있으면지워도됨
            intent.putExtra("name",article.drinkName)
            intent.putExtra("img", article.imgurl)
            intent.putExtra("caffeine", article.caffeine)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
            //카페인 마시기로 이동
        }}

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txt: TextView = itemView.findViewById(R.id.txt_name)
        private val img: ImageView = itemView.findViewById(R.id.img_caffeine)
        private val caf: TextView = itemView.findViewById(R.id.txt_caffeine)

        fun bind(article: Drinks){
            txt.text = article.drinkName
            caf.text = article.caffeine.toString() +"mg"

            if(article.imgurl == "")
                Glide.with(itemView).load(R.drawable.coffee_sample).into(img)
            else if ( article.imgurl == "url") Glide.with(itemView).load(R.drawable.cola_sample).into(img)
            else Glide.with(itemView).load(article.imgurl).override(500,).into(img)

        }

    }


}