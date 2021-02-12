package com.edwin.toyrobot.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edwin.toyrobot.R
import com.edwin.toyrobot.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.MainActivityView {

    private lateinit var mainActivityPresenter: MainActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(this)

        send_commands_btn.setOnClickListener {
            val commandText: String = command_et.text.toString()

            if (commandText == "")
                return@setOnClickListener

            mainActivityPresenter.processCommands(commandText.toUpperCase(Locale.getDefault()))
            command_et.setText("")
        }
    }

    override fun updateLog(data: String) {
        val currentLogString = "${log_data_tv.text}\n$data"
        log_data_tv.text = currentLogString
    }
}