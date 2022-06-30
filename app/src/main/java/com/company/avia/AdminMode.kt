package com.company.avia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.avia.databinding.ActivityAdminModeBinding
import com.google.firebase.database.*

class AdminMode : AppCompatActivity(), RecAdapter.ClickListener {
    lateinit var binding: ActivityAdminModeBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var raceListAdapter:RecAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAdmin.setOnClickListener {
            startActivity(Intent(this,AddRace::class.java))
        }
        binding.updateAdmin.setOnClickListener {
            raceListAdapter?.loadListToAdapter(getList())
        }


        binding.recyclerViewAdmin.layoutManager= LinearLayoutManager(this)
        raceListAdapter= RecAdapter(this)
        binding.recyclerViewAdmin.adapter=raceListAdapter
        raceListAdapter?.loadListToAdapter(getList())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =raceListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Race").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Race").child(id).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdmin)
    }


    fun getList():ArrayList<Race>{



        val commonList=ArrayList<Race>()
        database.getReference("Race").get().addOnSuccessListener {
            for (raced in it.children){
                var race=raced.getValue(Race::class.java)
                if(race!=null){
                    commonList.add(race)
                    raceListAdapter?.loadListToAdapter(commonList)
                }

            }
        }
        return commonList
    }
    override fun onClick(race: Race) {

    }

}
