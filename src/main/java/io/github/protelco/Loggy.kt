package io.github.protelco

class Loggy {
    fun beginSection(tag : String) {
        println("$tag ----------------------------------------------------------")
    }

    fun log(message: String){
        println(message)
    }
}
