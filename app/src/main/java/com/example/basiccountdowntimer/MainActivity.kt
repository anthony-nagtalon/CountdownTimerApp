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

    private lateinit var textViewCountdown : TextView
    private lateinit var buttonStartAndPause : Button
    private lateinit var buttonReset : Button

    private lateinit var countDownTimer : CountDownTimer

    private var timerRunning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCountdown = findViewById<TextView>(R.id.text_view_countdown)
        buttonStartAndPause = findViewById<Button>(R.id.button_start_and_pause)
        buttonReset = findViewById<Button>(R.id.button_reset)

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
                updateButtons()
            }
        }.start()

        timerRunning = true
        updateButtons()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerRunning = false
        updateButtons()
    }

    private fun resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        updateButtons()
    }

    private fun updateCountDownText() {
        val minutes : Int = ((timeLeftInMillis / 1000) / 60).toInt()
        val seconds : Int = ((timeLeftInMillis / 1000) % 60).toInt()

        val timeLeftFormatted : String = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        textViewCountdown.setText(timeLeftFormatted)
    }

    private fun updateButtons() {
        if(timerRunning) {
            buttonReset.visibility = View.INVISIBLE
            buttonStartAndPause.setText(R.string.pause_button)
        } else {
            buttonStartAndPause.setText(R.string.start_button)

            if(timeLeftInMillis < 1000) {
                buttonStartAndPause.visibility = View.INVISIBLE
            } else {
                buttonStartAndPause.visibility = View.VISIBLE
            }

            if (timeLeftInMillis < START_TIME_IN_MILLIS) {
                buttonReset.visibility = View.VISIBLE
            } else {
                buttonReset.visibility = View.INVISIBLE
            }
        }
    }
}