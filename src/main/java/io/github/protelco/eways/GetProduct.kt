package io.github.protelco.eways

import io.github.protelco.eways.SoapWrapper.Companion.configsList
import io.github.protelco.utils.LogUtils
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import javax.xml.parsers.DocumentBuilderFactory

class GetProduct {

    companion object {
        private val URL = configsList[0].url
        private val NAMESPACE = configsList[0].nameSpace
        private val METHOD_NAME = configsList[0].methodName
        private val SOAP_ACTION = configsList[0].soapAction
        private val USER_NAME = configsList[0].userName
    }

    fun call(transactionId: String): GetProductResult {

        val request = SoapObject(NAMESPACE, METHOD_NAME)
        request.addProperty("TransactionID", transactionId)
        request.addProperty("UserName", USER_NAME)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.implicitTypes = true
        envelope.setOutputSoapObject(request)
        envelope.dotNet = true

        val httpTransport = HttpTransportSE(URL)
        httpTransport.debug = true

        var status = ""
        var message = ""

        return try {
            LogUtils.log("requesting GetProducts")
            httpTransport.call(SOAP_ACTION, envelope)
            LogUtils.log("Getting response of GetProducts")
            val response: SoapObject = envelope.response as SoapObject

            status = response.getProperty("Status").toString()
            message = response.getProperty("Message").toString()
            val result: GetProductResult = parseResult(response.getProperty("Result").toString())

            result.copy(status = status, message = message)

        } catch (e: Exception) {
            LogUtils.log(e.message.toString())
            GetProductResult(status = status, message = message, uuid = "", transactionId = "")
        }
    }

    private fun parseResult(xmlResponse: String): GetProductResult {
        val docBuildFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuildFactory.newDocumentBuilder()
        val doc = docBuilder.parse(ByteArrayInputStream(xmlResponse.toByteArray(Charset.forName("UTF-8"))))
        doc.documentElement.normalize()

        val nodeList: NodeList = doc.documentElement.getElementsByTagName("Requirements")

        var uuid = ""
        var transactionId = ""

        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)
            if (node.nodeName == "Requirements") {
                val childNodes = node.childNodes
                for (k in 0 until childNodes.length) {
                    for (j in 0 until childNodes.item(k).attributes.length) {
                        val data = childNodes.item(k).textContent
                        val attributeValue = childNodes.item(k).attributes.item(j).nodeValue

                        if (attributeValue == "UUID") {
                            uuid = data
                        }

                        if (attributeValue == "TransactionID") {
                            transactionId = data
                        }
                    }
                }
            }
        }

        return GetProductResult(uuid = uuid, transactionId = transactionId)
    }

    data class GetProductResult(var status: String = "", var message: String = "", val uuid: String, val transactionId: String)
}