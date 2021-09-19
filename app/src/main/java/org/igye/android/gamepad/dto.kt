package org.igye.android.gamepad

data class BeErr(val code:Int, val msg: String)
data class BeRespose<T>(val data: T? = null, val err: BeErr? = null) {
    fun <B> mapData(mapper:(T) -> B): BeRespose<B> = if (data != null) {
        BeRespose(data = mapper(data))
    } else {
        (this as BeRespose<B>)
    }
}
data class ListOfItems<T>(val complete: Boolean, val items: List<T>)

data class ControllerEvent(val keyCode:Int, val eventTime:Long)
data class Cell(val x: Int, val y: Int)

enum class UserInput {
    LEFT, RIGHT, UP, DOWN, ENTER, ESCAPE,
    A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
    _0,_1,_2,_3,_4,_5,_6,_7,_8,_9
}