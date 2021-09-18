package org.igye.android.gamepad

import android.util.Log

class LoggerImpl(private val loggerName: String) {
    private val tag = "gp/$loggerName"
    private val defaultFormat = "%s"

    fun getName(): String {
        return loggerName
    }

    fun isTraceEnabled(): Boolean {
        return true
    }

    fun trace(msg: String?) {
        Log.v(tag, msg?:"")
    }

    fun trace(format: String?, arg: Any?) {
        Log.v(tag, String.format(format?: defaultFormat, arg))
    }

    fun trace(format: String?, arg1: Any?, arg2: Any?) {
        Log.v(tag, String.format(format?:defaultFormat, arg1, arg2))
    }

    fun trace(format: String?, vararg arguments: Any?) {
        Log.v(tag, String.format(format?:defaultFormat, *arguments))
    }

    fun trace(msg: String?, t: Throwable?) {
        Log.v(tag, msg, t)
    }

    fun isDebugEnabled(): Boolean {
        return true
    }

    fun debug(msg: String?) {
        Log.d(tag, msg?:"")
    }

    fun debug(format: String?, arg: Any?) {
        Log.d(tag, String.format(format?: defaultFormat, arg))
    }

    fun debug(format: String?, arg1: Any?, arg2: Any?) {
        Log.d(tag, String.format(format?:defaultFormat, arg1, arg2))
    }

    fun debug(format: String?, vararg arguments: Any?) {
        Log.d(tag, String.format(format?:defaultFormat, *arguments))
    }

    fun debug(msg: String?, t: Throwable?) {
        Log.d(tag, msg, t)
    }

    fun isInfoEnabled(): Boolean {
        return true
    }

    fun info(msg: String?) {
        Log.i(tag, msg?:"")
    }

    fun info(format: String?, arg: Any?) {
        Log.i(tag, String.format(format?: defaultFormat, arg))
    }

    fun info(format: String?, arg1: Any?, arg2: Any?) {
        Log.i(tag, String.format(format?:defaultFormat, arg1, arg2))
    }

    fun info(format: String?, vararg arguments: Any?) {
        Log.i(tag, String.format(format?:defaultFormat, *arguments))
    }

    fun info(msg: String?, t: Throwable?) {
        Log.i(tag, msg, t)
    }

    fun isWarnEnabled(): Boolean {
        return true
    }

    fun warn(msg: String?) {
        Log.w(tag, msg?:"")
    }

    fun warn(format: String?, arg: Any?) {
        Log.w(tag, String.format(format?: defaultFormat, arg))
    }

    fun warn(format: String?, vararg arguments: Any?) {
        Log.w(tag, String.format(format?:defaultFormat, *arguments))
    }

    fun warn(format: String?, arg1: Any?, arg2: Any?) {
        Log.w(tag, String.format(format?:defaultFormat, arg1, arg2))
    }

    fun warn(msg: String?, t: Throwable?) {
        Log.w(tag, msg, t)
    }

    fun isErrorEnabled(): Boolean {
        return true
    }

    fun error(msg: String?) {
        Log.e(tag, msg?:"")
    }

    fun error(format: String?, arg: Any?) {
        Log.e(tag, String.format(format?: defaultFormat, arg))
    }

    fun error(format: String?, arg1: Any?, arg2: Any?) {
        Log.e(tag, String.format(format?:defaultFormat, arg1, arg2))
    }

    fun error(format: String?, vararg arguments: Any?) {
        Log.e(tag, String.format(format?:defaultFormat, *arguments))
    }

    fun error(msg: String?, t: Throwable?) {
        Log.e(tag, msg, t)
    }
}