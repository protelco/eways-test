package io.github.protelco

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

data class Configurations(val url: String, val nameSpace: String, val soapAction: String, val methodName: String, val userName: String)

open class SoapWrapper {
    companion object {

        val configsList : List<Configurations> = configs()

        private fun configs(): List<Configurations> {
            val inputStream = FileInputStream("configs.json")
            val buf = BufferedReader(InputStreamReader(inputStream))
            var line = buf.readLine()
            val sb = StringBuilder()
            while(line != null)
            { sb.append(line).append("\n")
                line = buf.readLine()
            }
            val fileAsString = sb.toString();
            return Gson().fromJson(fileAsString, Array<Configurations>::class.java).asList()
        }
    }
}