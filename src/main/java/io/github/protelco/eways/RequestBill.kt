package io.github.protelco.eways

import io.github.protelco.eways.SoapWrapper.Companion.configsList
import io.github.protelco.utils.LogUtils
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class RequestBill {

    companion object {
        private val URL = configsList[1].url
        private val NAMESPACE = configsList[1].nameSpace
        private val METHOD_NAME = configsList[1].methodName
        private val SOAP_ACTION = configsList[1].soapAction
        private val USER_NAME = configsList[1].userName
    }

    fun call(requestId: String, billId: String, payId: String, optionalParam: String = "") {
        val request = SoapObject(NAMESPACE, METHOD_NAME)
        request.addProperty("RequestID", requestId)
        request.addProperty("SitePassword", USER_NAME)
        request.addProperty("BIllID", billId)
        request.addProperty("PayID", payId)
        request.addProperty("OptionalParam", optionalParam)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.implicitTypes = true
        envelope.setOutputSoapObject(request)
        envelope.dotNet = true

        val httpTransport = HttpTransportSE(URL)
        httpTransport.debug = true

        var status = ""
        var message = ""

       try {

           LogUtils.log("request RequestBill")
           httpTransport.call(SOAP_ACTION, envelope)
           LogUtils.log("Getting response of RequestBill")
           val response: SoapObject = envelope.response as SoapObject

           for(i in 0 until response.propertyCount) {
               LogUtils.log(response.getProperty(i).toString())
           }

        } catch (e : Exception) {
           LogUtils.log(e.localizedMessage)
        }
    }

    fun parseResult(xmlResponse : String) {

    }

    data class RequestBillResult(val status : String = "", var message : String = "")
}