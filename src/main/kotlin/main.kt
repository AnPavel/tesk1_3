fun main() {
    print("Введите номер задания или 0 для выхода: ")
    val nomerzadania: Int = readLine()!!.toInt()
    when (nomerzadania) {
        1 -> task1()
        2 -> task2()
        else -> 0
    }
}


fun task1 () {

    println("Задание №1. Только что")
    print("Введите кол-во секунд: ")
    val elapsedTime: Int = readLine()!!.toInt()   //Ввод прошедших секунд

    agoToText(elapsedTime)

}


fun agoToText (timeVisiting:Int) {
    var solution= ""
    when (timeVisiting) {
        in 1..60 -> solution = "только что"
        in 61..3600 -> solution = agoToMinut(timeVisiting)
        in 3601..86400 -> solution = agoToHour(timeVisiting)
        in 86401..259200 -> solution = agoToDay(timeVisiting)
        else -> println("давно")
    }
    println("был(а) $solution назад")
}

fun agoToMinut (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 60 % 10) {
        0 -> solution = "${timeVisiting / 60} минут"
        1 -> solution = "${timeVisiting / 60} минуту"
        in 2..4 -> solution = "${timeVisiting / 60} минуты"
        in 5..10 -> solution = "${timeVisiting / 60} минут"
    }

    when (timeVisiting / 60) {
        in 0..1 -> solution = "${timeVisiting / 60} минуту"
        in 2..4 -> solution = "${timeVisiting / 60} минуты"
        in 5..20 -> solution = "${timeVisiting / 60} минут"
    }
    return solution
}

fun agoToHour (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 3600 % 10) {
        1 -> solution = "${timeVisiting / 3600} час"
        in 2..4 -> solution = "${timeVisiting / 3600} часа"
        in 5..10 -> solution = "${timeVisiting / 3600} часов"
    }

    when (timeVisiting / 3600) {
        in 0..1 -> solution = "${timeVisiting / 3600} час"
        in 2..4 -> solution = "${timeVisiting / 3600} часа"
        in 5..20 -> solution = "${timeVisiting / 3600} часов"
    }
    return solution
}

fun agoToDay (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 86400) {
        1 -> solution = "${timeVisiting / 86400} сутки"
        in 2..3 -> solution = "${timeVisiting / 86400} суток"
        else -> solution = "давно"
    }
    return solution
}


fun task2 () {
    var typeCard = "VK Pay"
    val previousSumma = 0.0

    println("Задание №2. Денежные переводы")
    print("Введите сумму перевода в рублях: ")
    val amount: Float = readLine()!!.toFloat()   //Ввод суммы перевода

    //Первый символ карт для определения типа карты = 2 -MIR, 4 -VISA, 5 -Master, 6 -Maestro, 9 -VK Pay???
    print("Введите номер (первую цифру) карты для перевода: ")
    val numberCard = readLine()!!.toCharArray()           //Ввод номера карты для определения ТИПА КАРТЫ
    val symbolCard = (numberCard.first())
    when (symbolCard.toString()) {
        "2" -> typeCard = "MIR Card"
        "4" -> typeCard = "VISA Card"
        "5" -> typeCard = "MasterCard"
        "6" -> typeCard = "MaestroCard"
        else -> typeCard = "VK Pay"
    }

    //расчет комиссии и суммы перевода
    calculationCommission(typeCard, previousSumma, amount)
    println("Расчет завершен")

}

fun calculationCommission(typeCard: String, previousSumma: Double, amount: Float) {
    val procentKomissMaster = 0.6    // процент комиссии карт Master
    val summaMinKomissMaster = 20       // сумма комиссии Master
    val procentKomissVisaMir = 0.75  // процент комиссии карт VISA / MIR
    val summaMinKomissVisaMir = 35      // сумма мин комиссии карт VISA / MIR
    val maxLimitTodayCard = 150_000          // максимальный лимит в СУТКИ по картам (кроме VK Pay)
    val maxLimitMonthCard = 600_000          // максимальный лимит в МЕСЯЦ по картам (кроме VK Pay)
    val maxLimitTodayVKPay = 15_000          // максимальный лимит в СУТКИ VK Pay
    val maxLimitMonthVKPay = 40_000          // максимальный лимит в МЕСЯЦ VK Pay
    var summaTransfer = 0.0                  // сумма перевода

    println(typeCard) //вывести на экран ТИП карты

    if (typeCard == "VK Pay") {
        // проверка на месячный лимит для перевода
        if (amount < maxLimitMonthVKPay) {
            // проверка на суточный лимит для перевода
            if (amount < maxLimitTodayVKPay) {
                summaTransfer = amount - previousSumma
                println("Сумма перевода: $summaTransfer")
            } else {
                println("Превышен суточный лимит на перевод по карте VK Pay")
            }
        } else {
            println("Превышен месячный лимит на перевод по карте VK Pay")
        }
    }

    if ((amount + previousSumma) < maxLimitMonthCard) {
        if (amount < maxLimitTodayCard) {
            if (typeCard == "MIR Card" || typeCard == "VISA Card") {
                //расчет комиссии
                if ((amount / 100 * procentKomissVisaMir) < summaMinKomissVisaMir) {
                    summaTransfer = (amount - summaMinKomissVisaMir).toDouble()
                    println("Сумма комиссии: $summaMinKomissVisaMir")
                } else {
                    summaTransfer = amount - (amount / 100 * procentKomissVisaMir)
                    println("Сумма комиссии: " + (amount / 100 * procentKomissVisaMir))
                }
                println("Сумма перевода: $summaTransfer")
            }

            if (typeCard == "MasterCard" || typeCard == "MaestroCard") {
                summaTransfer = (amount - (amount / 100 * procentKomissMaster)) - summaMinKomissMaster
                println("Сумма комиссии: " + ((amount / 100 * procentKomissMaster) + summaMinKomissMaster))
                println("Сумма перевода: $summaTransfer")
            }

        } else {
            println("Превышен лимит по карте по сумме перевода в сутки")
        }

    } else {
            println("Превышен лимит по карте по сумме перевода в месяц")
    }

}