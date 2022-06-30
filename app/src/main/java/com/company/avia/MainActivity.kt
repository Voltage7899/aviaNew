package com.company.avia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.avia.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), RecAdapter.ClickListener {

    lateinit var binding: ActivityMainBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var raceListAdapter:RecAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navLeftMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.signIn->{
                    startActivity(Intent(this,Sign::class.java))
                }
                R.id.regist->{
                    startActivity(Intent(this,Registr::class.java))

                }
            }
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }

        binding.recyclerUser.layoutManager= LinearLayoutManager(this)
        raceListAdapter= RecAdapter(this)
        binding.recyclerUser.adapter=raceListAdapter
        raceListAdapter?.loadListToAdapter(getList())
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
        startActivity(Intent(this, UserDetail::class.java).apply {
            putExtra("chosenRace",race)

        })
    }
}