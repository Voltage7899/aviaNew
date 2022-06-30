package com.company.avia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.avia.databinding.ActivityUserDetailBinding
import com.squareup.picasso.Picasso

class UserDetail : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("chosenRace") as Race
        Picasso.get().load(item.image).fit().into(binding.imageItem)

        binding.priceItem.setText(item.price)
        binding.departureItem.setText(item.departure)
        binding.landingItem.setText(item.landing)
        binding.fromItem.setText(item.from)
        binding.toItem.setText(item.to)

        binding.buyItem.setOnClickListener{

            Toast.makeText(this@UserDetail, "Данные высланы в смс", Toast.LENGTH_SHORT).show()
        }
    }
}