package com.example.basiccountdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val START_TIME_IN_MILLIS : Long = 600000
    private var timeLeftInMillis : Long = START_TIME_IN_MILLIS

    private val textViewCountdown : TextView = findViewById<TextView>(R.id.text_view_countdown)
    private val buttonStartAndPause : Button = findViewById<Button>(R.id.button_start_and_pause)
    private val buttonReset : Button = findViewById<Button>(R.id.button_reset)

    private lateinit var countDownTimer : CountDownTimer

    private var timerRunning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStartAndPause.setOnClickListener {
            if(timerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        buttonReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        countDownTimer = object: CountDownTimer(timeLeftInMillis,1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                timerRunning = false
                buttonStartAndPause.setText(R.string.start_button)
                buttonStartAndPause.visibility = View.INVISIBLE
                buttonReset.visibility = View.VISIBLE
            }
        }.start()

        timerRunning = true
        buttonStartAndPause.setText(R.string.pause_button)
        buttonReset.visibility = View.INVISIBLE
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerRunning = false
        buttonStartAndPause.setText(R.string.start_button)
        buttonReset.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        buttonReset.visibility = View.INVISIBLE
        buttonStartAndPause.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val minutes : Int = ((timeLeftInMillis / 1000) / 60).toInt()
        val seconds : Int = ((timeLeftInMillis / 1000) % 60).toInt()

        val timeLeftFormatted : String = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        textViewCountdown.setText(timeLeftFormatted)
    }
}