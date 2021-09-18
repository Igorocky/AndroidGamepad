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

data class UserInput(val keyCode:Int, val eventTime:Long)
data class Cell(val x: Int, val y: Int)