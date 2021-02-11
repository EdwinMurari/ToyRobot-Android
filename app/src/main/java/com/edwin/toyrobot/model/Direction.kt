package com.edwin.toyrobot.model

enum class Direction{
    North,
    South,
    West,
    East;

    fun left(): Direction{
        return when(this){
            North -> West
            South -> East
            West -> South
            East -> North
        }
    }

    fun right(): Direction{
        return when(this){
            North -> East
            South -> West
            West -> North
            East -> South
        }
    }
}
