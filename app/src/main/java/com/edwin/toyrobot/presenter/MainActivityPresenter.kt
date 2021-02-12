package com.edwin.toyrobot.presenter

import android.graphics.Point
import android.util.Log
import com.edwin.toyrobot.model.*
import java.util.*

class MainActivityPresenter(private var mainActivityView: MainActivityView) {

    fun processCommands(commandsString: String) {
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

    fun testRobot() {
        val toyRobot = ToyRobot()
        toyRobot.place(Pose(Point(5, 5), Direction.EAST))
        toyRobot.move()
        toyRobot.move()
        toyRobot.left()
        toyRobot.move()
        Log.e("TEST", "Reporting current pose: ${toyRobot.report()}")
    }

    interface MainActivityView {
        fun updateLog(data: String)
    }
}