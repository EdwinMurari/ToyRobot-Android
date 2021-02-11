package com.edwin.toyrobot.view

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.edwin.toyrobot.R
import com.edwin.toyrobot.model.Direction
import com.edwin.toyrobot.model.Pose
import com.edwin.toyrobot.model.ToyRobot
import com.edwin.toyrobot.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityPresenter.MainActivityView {
    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(this)
        mainActivityPresenter.processCommands("PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH")
    }

    override fun updateLog(data: String) {
        TODO("Not yet implemented")
    }
}