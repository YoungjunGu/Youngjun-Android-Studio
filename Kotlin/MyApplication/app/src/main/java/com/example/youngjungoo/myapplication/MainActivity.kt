package com.example.youngjungoo.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        submitBtn.setOnClickListener {
            /*
           val intent = Intent(this, SeconActivity::class.java)
           intent.putExtra("weight", ageEditText.text.toString())
           intent.putExtra("height", heightEditText.text.toString())
           startActivity(intent)
            */

            // Anko를 활용한 intent 방법
            startActivity<SeconActivity>(
                    "age" to ageEditText.text.toString(),
                    "height" to heightEditText.text.toString()
            )
        }


    }
}
