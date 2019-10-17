package io.github.protelco

object LogUtils {
    fun beginSection(tag : String) {
        println("$tag ----------------------------------------------------------")
    }

    fun log(message: String){
        println(message)
    }
}
