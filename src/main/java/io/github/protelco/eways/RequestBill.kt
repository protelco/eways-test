package io.github.protelco.eways

import io.github.protelco.eways.SoapWrapper.Companion.configsList

class RequestBill {

    companion object {
        private val URL = configsList[1].url
        private val NAMESPACE = configsList[1].nameSpace
        private val METHOD_NAME = configsList[1].methodName
        private val SOAP_ACTION = configsList[1].soapAction
        private val USER_NAME = configsList[1].userName
    }

    fun call() {
    }
}