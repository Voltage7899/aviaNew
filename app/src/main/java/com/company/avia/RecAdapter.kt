package com.company.avia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.avia.databinding.ElementBinding
import com.squareup.picasso.Picasso

class RecAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<RecAdapter.RaceViewHolder>() {

    private var raceListInAdapter= ArrayList<Race>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecAdapter.RaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element,parent,false)

        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecAdapter.RaceViewHolder, position: Int) {
        holder.bind(raceListInAdapter[position],clickListener)
    }

    override fun getItemCount(): Int {
        return raceListInAdapter.size
    }

    class RaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementBinding.bind(itemView)
        fun bind(race: Race, clickListener: ClickListener){
            binding.priceEl.text=race.price
            binding.fromEl.text=race.from
            binding.toEl.text=race.to
            Picasso.get().load(race.image).fit().into(binding.imageEl)
            itemView.setOnClickListener{

                clickListener.onClick(race)
            }

        }
    }
    fun loadListToAdapter(productList:ArrayList<Race>){
        raceListInAdapter= productList
        notifyDataSetChanged()
    }

    interface ClickListener{
        fun onClick(product: Race){

        }
    }
    fun deleteItem(i:Int):String?{
        var id=raceListInAdapter.get(i).id

        raceListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}