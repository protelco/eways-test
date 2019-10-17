package io.github.protelco

class RequestBillSoapWrapper {

    companion object {
        private val URL = SoapWrapper.configsList[1].url
        private val NAMESPACE = SoapWrapper.configsList[1].nameSpace
        private val METHOD_NAME = SoapWrapper.configsList[1].methodName
        private val SOAP_ACTION = SoapWrapper.configsList[1].soapAction
        private val USER_NAME = SoapWrapper.configsList[1].userName
    }

    fun call() {
    }
}