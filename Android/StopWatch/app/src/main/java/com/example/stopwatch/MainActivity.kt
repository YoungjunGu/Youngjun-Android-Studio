package com.example.stopwatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        playFab.setOnClickListener {
            // 버튼이 눌러졌으면 flag 값을 반전 시키고
            isRunning = !isRunning

            if(isRunning) {
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        playFab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++

            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milTextView.text = "$milli"
            }
        }
    }

    private  fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB: ${lapTime / 100}.${lapTime%100}"
        // 인덱스 값을 추가하면 해당 위치에 뷰가 추가된다. 항상 맨위에 텍스트 뷰를 추가하는 코드


        lapLayout.addView(textView, 0)
        lap++

    }

    private fun pause() {
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun reset() {
        timerTask?.cancel()

        this.time = 0

        this.isRunning = false
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milTextView.text = "00"

        this.lap = 1
        // 모든 레이아웃 subView 삭제
        lapLayout.removeAllViews()
    }
}
