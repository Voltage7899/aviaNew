package com.company.avia

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.avia.databinding.ActivityMainBinding
import com.company.avia.databinding.ActivityRegistrBinding
import com.google.firebase.database.*

class Registr : AppCompatActivity() {

    lateinit var binding: ActivityRegistrBinding
    private var register: Button? = null

    //Переменная типа ссылки базы данных
    private var database: DatabaseReference? = null

    //Название создаваемой таблицы
    private val TABLE_NAME = "User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Получаем ссылку на таблицу базы данных
        database = FirebaseDatabase.getInstance().getReference(TABLE_NAME)

        //Задаем слушателя
        binding.regReg.setOnClickListener(View.OnClickListener {
            //В переменные типа стринг пихаем введенные данные из полей
            val name = binding.nameReg.getText().toString()
            val phone = binding.phoneReg.getText().toString()
            val pass = binding.passReg.getText().toString()

            //Создаем разметку данных через ХэшМэп
            val userDataMap = HashMap<String, Any>()
            userDataMap["name"] = name
            userDataMap["phone"] = phone
            userDataMap["pass"] = pass

            //Устанавливаем слушателя на изменение данных в базе данных
            database?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Как только мы считали данные с базы данных,нам приходят данные в качестве снэпшота
                    if (!snapshot.child(phone)
                            .exists()
                    ) { //Проверяем,есть ли введенный телефон уже в базе данных,если нет,то добавляем хэшмэп в бд
                        database!!.child(phone).updateChildren(userDataMap)
                        Toast.makeText(this@Registr, "Вы зарегестрированы", Toast.LENGTH_SHORT)
                            .show()

                        finish()
                    } else {
                        Toast.makeText(
                            this@Registr,
                            "Пользователь с такими данными уже есть",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        })
    }
}