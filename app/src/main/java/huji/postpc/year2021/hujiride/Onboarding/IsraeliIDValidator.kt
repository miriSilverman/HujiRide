package huji.postpc.year2021.hujiride.Onboarding

// Algorithm from: http://halemo.net/info/idcard/

val regexValidator = Regex("^\\d{1,9}$")
val numbersTransformation = arrayListOf(0, 2, 4, 6, 8, 1, 3, 5, 7, 9)
fun validateIsraeliID(ID: String): Boolean {
    if (!ID.matches(regexValidator)) {
        return false
    }

    val paddID = ID.padStart(9, '0')
    val numArr = paddID.map {
        it.digitToInt()
    }.toMutableList()

    for (i in 1..8 step 2){
        numArr[i] = numbersTransformation[numArr[i]]
    }

    return numArr.reduce { acc, i -> acc + i } % 10 == 0
}

fun main() {
    assert(validateIsraeliID("208624908"))
    assert(validateIsraeliID("57363467"))
    assert(validateIsraeliID("208086199"))
    assert(!validateIsraeliID("208086191"))
    assert(!validateIsraeliID("208186199"))
    assert(!validateIsraeliID("11111185199"))
    assert(!validateIsraeliID("208624901"))
    assert(!validateIsraeliID("208624902"))
    assert(!validateIsraeliID("208624903"))
    assert(!validateIsraeliID("208624904"))
    assert(!validateIsraeliID("208624905"))
    assert(!validateIsraeliID("57363469"))
}