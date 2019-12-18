package com.clarkgable1.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            isRunning = !isRunning
            if (isRunning) start()
            else pause()
        }

        resetFab.setOnClickListener { reset()   }
        lapButton.setOnClickListener { recordLapTime() }

    }

    private fun pause() {
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }
    private fun start() {
        playFab.setImageResource(R.drawable.ic_pause_black_24dp)
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                if (isRunning) {
                    secTextView.text = "$sec"
                    milliTextView.text = "$milli"
                }
            }
        }
    }

    private fun reset() {
        timerTask?.cancel()

        //init
        time = 0
        isRunning = false
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        //remove all laptime
        scrollView.removeAllViews()
        lap = 1
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB : ${lapTime / 100}.${lapTime % 100}"

        // add laptime to the top
        //스크롤뷰 안의 기본 포함된 리니어 레이아웃에 텍스트 뷰 넣는 코드.
        // 여긴엔 어떤 뷰도 들어간다고 함.
        lapLayout.addView(textView, 0)
        lap++
    }

}
