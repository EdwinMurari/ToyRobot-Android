package com.edwin.toyrobot.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edwin.toyrobot.R
import com.edwin.toyrobot.presenter.MainActivityPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.MainActivityView {

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(this)

        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == PICK_FILE_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    mainActivityPresenter.readFile(intent, contentResolver)
                } catch (exception: Exception) {
                    Toast.makeText(
                        this,
                        "Something went wrong when reading the file.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initListeners() {
        send_commands_btn.setOnClickListener {
            mainActivityPresenter.processCommands(
                command_et.text.toString().toUpperCase(Locale.getDefault())
            )
        }

        attach_commands_btn.setOnClickListener {
            val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
            chooseFile.type = "text/plain"
            startActivityForResult(
                Intent.createChooser(chooseFile, "Choose a file"),
                PICK_FILE_RESULT_CODE
            )
        }

        /*tab_lyt.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

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
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })*/
    }

    override fun updateLog(data: String) {
        val currentLogString = "${log_data_tv.text}\n$data"
        log_data_tv.text = currentLogString

        log_scrollview.post {
            log_scrollview.fullScroll(View.FOCUS_DOWN)
        }
    }

    override fun clearCommandTextBox() {
        command_et.setText("")
    }

    companion object {
        private const val PICK_FILE_RESULT_CODE: Int = 100
    }
}