package io.github.protelco

import io.github.the724.IrBill
import kotlin.random.Random

fun main() {
    val loggy = Loggy()

    // 1. get billId and paymentId
    loggy.beginSection("1-CREATE RANDOM BILL")

    val caseNumber = Random(System.currentTimeMillis()).nextInt(1366923)
    val randomBill = BillGenerator().newBill(1, 310, 1, 1, caseNumber, 1000.0, false)
    val validatedBill = IrBill.parseBillData(randomBill.billId, randomBill.paymentId)

    loggy.log("generated bill data")
    loggy.log(validatedBill.toString())

    if (validatedBill != null &&
            IrBill.validateBillId(randomBill.billId) &&
            IrBill.validatePaymentId(randomBill.billId, randomBill.paymentId)) {

        // 2. create transaction id and save in log file
        loggy.beginSection("2- CREATE TRANSACTION ID")
        val transactionId = Random(System.currentTimeMillis()).nextInt()
        loggy.log("Transaction id : $transactionId")

        // 3. get UUID from eways server and save in log file

        // 4. call request bill to pay the bill and save the response in log file

        // 5. read the log file and call get status to show the result

        // 6. call
    } else {
        loggy.beginSection("Error happened")
        loggy.log("random bill validation failed")
    }
}