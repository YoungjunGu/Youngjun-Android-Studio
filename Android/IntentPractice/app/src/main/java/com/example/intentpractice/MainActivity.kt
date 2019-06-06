package com.example.intentpractice

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        submitBtn.setOnClickListener {

            saveData(nameEditText.text.toString(), ageEditText.text.toString().toInt())

            startActivity<SecondActivity>(
                "name" to nameEditText.text.toString(),
                "age" to ageEditText.text.toString()
            )
        }
    }

    // 데이터 저장하기
    private fun saveData(name: String, age: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()


        editor.putString("KEY_NAME", name)
            .putInt("KEY_AGE", age)
            .apply()
    }

    private fun loadData() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        val name = pref.getString("KEY_NAME", "")
        val age = pref.getInt("KEY_AGE",0)

        if(name != null && age != 0) {
            nameEditText.setText(name.toString())
            ageEditText.setText(age.toString())
        }

    }
}
