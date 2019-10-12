package io.github.protelco

data class RandomBill(val billId: String, val paymentId: String)

class BillGenerator {

    fun newBill(
        serviceTypeCode: Int,
        companyCode: Int,
        yearCode: Int,
        durationCode: Int,
        caseNumber: Int,
        amount: Double,
        zeroPadded: Boolean
    ): RandomBill {
        if (serviceTypeCode < 1 || serviceTypeCode > 9) {
            throw Exception("ServiceTypeCode Type must between 1 to 9")
        }
        if (companyCode < 100 || companyCode > 999) {
            throw Exception("CompanyCode Type must between 100 to 999")
        }
        if (yearCode < 0 || yearCode > 9) {
            throw Exception("YearCode Type must between 0 to 9")
        }
        if (durationCode < 1 || durationCode > 12) {
            throw Exception("DurationCode Type must between 1 to 12")
        }

        if (caseNumber < 1 || caseNumber > 99999999) {
            throw Exception("SubID Type must between 1 to 99999999")
        }
        if (amount < 1 || amount > 99999999) {
            throw Exception("ValueToPay Type must between 1 to 99999999")
        }

        var billId = (caseNumber).toString() + (companyCode).toString() + (serviceTypeCode).toString()
        billId += getCheckDigit(billId)

        var duration = (durationCode).toString()
        if (duration.length < 2) {
            duration = "0$duration"
        }
        var paymentId = ((amount / 1000).toInt()).toString() + (yearCode).toString() + duration
        paymentId += getCheckDigit(paymentId)
        val totalCheckDigit = billId + paymentId
        paymentId += getCheckDigit(totalCheckDigit)
        if (zeroPadded) {
            billId = zeroPad(billId)
            paymentId = zeroPad(paymentId)
        }
        return RandomBill(billId, paymentId)
    }

    private fun getCheckDigit(input: String): Int {
        val charArray = input.toCharArray()
        var multiplier = 2
        var sum = 0
        for (index in charArray.indices.reversed()) {
            sum += Character.getNumericValue(charArray[index]) * multiplier
            multiplier++
            if (multiplier > 7)
                multiplier = 2
        }
        val remainder = (sum % 11)
        var returnValue = -1
        if (remainder == 0 || remainder == 1) {
            returnValue = 0
        } else {
            returnValue = 11 - remainder
        }
        return returnValue
    }

    private fun zeroPad(token: String): String {
        var input = token
        while (input.length <= 13) {
            input = "0$input"
        }
        return input
    }
}