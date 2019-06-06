package com.example.intentpractice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.toast

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")

        setUpTextView(name,age)

    }

    fun setUpTextView(name:String, age: String):Unit {
        nameTextView.text = name
        ageTextVIew.text = age

        val ageInt = age.toInt()
        when {
            ageInt >= 25 -> resultTextView.text = "노땅"
            ageInt >= 23 -> resultTextView.text = "복학생"
            ageInt >= 21 -> resultTextView.text = "헌내기"
            ageInt >= 20 -> resultTextView.text = "새내기"
            else -> resultTextView.text = "대학생이 아닙니다."
        }

        // Anko를 활용한 toast
        // 기존의 toast : Toast.makeText(this, "출력", Toast.LENGTH_SHORT).show() // 자바와 동일
        toast(resultTextView.text.toString())

    }

}
