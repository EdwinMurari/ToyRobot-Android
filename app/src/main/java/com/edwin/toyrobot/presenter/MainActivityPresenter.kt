package com.edwin.toyrobot.presenter

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Point
import android.widget.Toast
import com.edwin.toyrobot.model.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivityPresenter(private var mainActivityView: MainActivityView) {

    fun processCommands(commandsString: String) {
        if (commandsString == "")
            return

        mainActivityView.updateLog("\nINPUT:")
        mainActivityView.updateLog(commandsString)
        mainActivityView.updateLog("OUTPUT:")

        val toyRobot = ToyRobot()
        val commands: List<String> = getCommandsAsList(commandsString)
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
        // TODO :: Handle invalid commands
        if (command == CommandConsts.PLACE && parameterString != null)
            bot.place(getPoseFromParameter(parameterString))
        else if (command == CommandConsts.MOVE)
            bot.move()
        else if (command == CommandConsts.LEFT)
            bot.left()
        else if (command == CommandConsts.RIGHT)
            bot.right()
        else if (command == CommandConsts.REPORT)
            mainActivityView.updateLog("Reporting pose: ${bot.report()}")
    }

    private fun getPoseFromParameter(parameterString: String): Pose {
        // TODO :: Handle invalid parameters
        val parameters: List<String> =
            parameterString.split(CommandConsts.PARAM_SEPARATOR.toRegex())

        return Pose(
            Point(parameters[0].toInt(), parameters[1].toInt()),
            Direction.valueOf(parameters[2])
        )
    }

    private fun getCommandsAsList(string: String): List<String> {
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