package io.github.protelco

import io.github.the724.IrBill
import kotlin.random.Random

fun main() {

    // 1. Generate a random bill, of UD type having a valid billId and paymentId
    LogUtils.beginSection("1-CREATE RANDOM BILL")

    val caseNumber = Random(System.currentTimeMillis()).nextInt(1366923)
    val randomBill = BillGenerator().newBill(8, 310, 1, 1, caseNumber, 1000.0, false)
    val validatedBill = IrBill.parseBillData(randomBill.billId, randomBill.paymentId)

    LogUtils.log("generated bill data")
    LogUtils.log(validatedBill.toString())

    /**
     * if the generated bill is valid, meaning its billId and paymentId is valid, then we proceed.
     * Note : Generating bills is not part of the project and has been added just for testing purposes
     */
    if (validatedBill != null &&
            IrBill.validateBillId(randomBill.billId) &&
            IrBill.validatePaymentId(randomBill.billId, randomBill.paymentId)) {

        // 2. create transaction id and save in log file
        LogUtils.beginSection("2- CREATE TRANSACTION ID")
        var transactionId = Random(System.currentTimeMillis()).nextInt(1366923)
        if (transactionId < 0) {
            transactionId *= -1
        }
        LogUtils.log("Transaction id : $transactionId")

        // 3. get UUID from eways server and save in log file
        val getProductResult = GetProductSoapWrapper().call(transactionId.toString())
        LogUtils.log(getProductResult.toString())

        // 4. call request bill to pay the bill and save the response in log file
        val requestBillResult = RequestBillSoapWrapper().call()

        // 5. read the log file and call get status to show the result

    } else {
        LogUtils.beginSection("Error happened")
        LogUtils.log("random bill validation failed")
    }
}