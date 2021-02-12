package com.edwin.toyrobot.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edwin.toyrobot.R
import com.edwin.toyrobot.presenter.MainActivityPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.MainActivityView {

    private lateinit var mainActivityPresenter: MainActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(this)

        initListeners()
    }

    private fun initListeners() {
        send_commands_btn.setOnClickListener {
            val commandText: String = command_et.text.toString()

            if (commandText == "")
                return@setOnClickListener

            mainActivityPresenter.processCommands(commandText.toUpperCase(Locale.getDefault()))
            command_et.setText("")
        }

        tab_lyt.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == tab_lyt.getTabAt(0)) {
                    graph_lyt.visibility = View.VISIBLE
                    log_lyt.visibility = View.INVISIBLE
                } else if (tab == tab_lyt.getTabAt(1)) {
                    graph_lyt.visibility = View.INVISIBLE
                    log_lyt.visibility = View.VISIBLE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    override fun updateLog(data: String) {
        val currentLogString = "${log_data_tv.text}\n$data"
        log_data_tv.text = currentLogString
    }
}