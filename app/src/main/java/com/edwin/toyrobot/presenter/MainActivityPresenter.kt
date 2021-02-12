package com.edwin.toyrobot.presenter

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Point
import com.edwin.toyrobot.model.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivityPresenter(private var mainActivityView: MainActivityView) {

    fun processCommands(commandsString: String) {
        if (commandsString == "")
            return

        mainActivityView.updateLog("\n###INPUT###")
        mainActivityView.updateLog(commandsString)
        mainActivityView.updateLog("###OUTPUT###")

        val toyRobot = ToyRobot()
        val commands: List<String> = breakCommandsIntoList(commandsString)
        val commandsIterator = commands.listIterator()

        while (commandsIterator.hasNext()) {
            val command = commandsIterator.next()
            if (command == CommandConsts.PLACE)
                runCommand(toyRobot, command, commandsIterator.next())
            else
                runCommand(toyRobot, command)
        }

        mainActivityView.clearCommandTextBox()
    }

    private fun runCommand(bot: ControllableBot, command: String, parameterString: String? = null) {
        when {
            command == CommandConsts.PLACE && parameterString != null -> {
                try {
                    bot.place(getPoseFromParameter(parameterString))
                } catch (exception: IllegalArgumentException) {
                    mainActivityView.updateLog("INVALID PARAMETER {$parameterString} for {$command}")
                }
            }
            command == CommandConsts.MOVE -> bot.move()
            command == CommandConsts.LEFT -> bot.left()
            command == CommandConsts.RIGHT -> bot.right()
            command == CommandConsts.REPORT -> mainActivityView.updateLog("Reporting pose: ${bot.report()}")
            else -> mainActivityView.updateLog("INVALID COMMAND: {$command}")
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getPoseFromParameter(parameterString: String): Pose {
        val parameters: List<String> =
            parameterString.split(CommandConsts.PARAM_SEPARATOR.toRegex())

        if (parameters.size != 3)
            throw IllegalArgumentException()

        return Pose(
            Point(parameters[0].toInt(), parameters[1].toInt()),
            Direction.valueOf(parameters[2])
        )
    }

    private fun breakCommandsIntoList(string: String): List<String> {
        return string.split(CommandConsts.COMMAND_SEPARATOR.toRegex())
    }

    @Throws(Exception::class)
    fun readFile(intent: Intent?, contentResolver: ContentResolver) {
        try {
            intent?.data?.let {
                contentResolver.openInputStream(it)
            }?.let {
                val bufferedReader = BufferedReader(InputStreamReader(it))
                while (true) {
                    val line: String? = bufferedReader.readLine() ?: break
                    line?.let { processCommands(line) }
                }
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

    interface MainActivityView {
        fun updateLog(data: String)
        fun clearCommandTextBox()
    }
}