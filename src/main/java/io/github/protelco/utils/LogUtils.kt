package io.github.protelco.utils

object LogUtils {
    fun beginSection(tag : String) {
        println("$tag ----------------------------------------------------------")
    }

    fun log(message: String){
        println(message)
    }
}
