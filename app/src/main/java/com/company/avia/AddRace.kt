package com.company.avia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.avia.databinding.ActivityAddRaceBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class AddRace : AppCompatActivity() {
    lateinit var binding: ActivityAddRaceBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddRaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {


            val id = Integer.toString((1 + Math.random() * 10000).toInt())
            val race=Race(
                binding.priceAdd.text.toString(),
                binding.departureAdd.text.toString(),
                binding.landingAdd.text.toString(),
                binding.fromAdd.text.toString(),
                binding.toAdd.text.toString(),
                binding.imageLink.text.toString(),
                id.toString()
            )
            database.getReference("Race").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(race.id.toString()).exists()){
                        Toast.makeText(this@AddRace,"Уже есть",Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Race").child(id).setValue(race)
                        startActivity(Intent(this@AddRace,AdminMode::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
        binding.imageAdd.setOnClickListener{

            try{
                Picasso.get().load(binding.imageLink.text.toString()).fit().into(binding.imageAdd)
            }
            catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку",Toast.LENGTH_SHORT).show()
            }




        }
    }


}