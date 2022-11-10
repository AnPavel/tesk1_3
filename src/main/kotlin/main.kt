fun main() {
    print("������� ����� ������� ��� 0 ��� ������: ")
    val nomerzadania: Int = readLine()!!.toInt()
    when (nomerzadania) {
        1 -> task1()
        2 -> task2()
        else -> 0
    }
}


fun task1 () {

    println("������� �1. ������ ���")
    print("������� ���-�� ������: ")
    val elapsedTime: Int = readLine()!!.toInt()   //���� ��������� ������

    agoToText(elapsedTime)

}


fun agoToText (timeVisiting:Int) {
    var solution= ""
    when (timeVisiting) {
        in 1..60 -> solution = "������ ���"
        in 61..3600 -> solution = agoToMinut(timeVisiting)
        in 3601..86400 -> solution = agoToHour(timeVisiting)
        in 86401..259200 -> solution = agoToDay(timeVisiting)
        else -> println("�����")
    }
    println("���(�) $solution �����")
}

fun agoToMinut (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 60 % 10) {
        0 -> solution = "${timeVisiting / 60} �����"
        1 -> solution = "${timeVisiting / 60} ������"
        in 2..4 -> solution = "${timeVisiting / 60} ������"
        in 5..10 -> solution = "${timeVisiting / 60} �����"
    }

    when (timeVisiting / 60) {
        in 0..1 -> solution = "${timeVisiting / 60} ������"
        in 2..4 -> solution = "${timeVisiting / 60} ������"
        in 5..20 -> solution = "${timeVisiting / 60} �����"
    }
    return solution
}

fun agoToHour (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 3600 % 10) {
        1 -> solution = "${timeVisiting / 3600} ���"
        in 2..4 -> solution = "${timeVisiting / 3600} ����"
        in 5..10 -> solution = "${timeVisiting / 3600} �����"
    }

    when (timeVisiting / 3600) {
        in 0..1 -> solution = "${timeVisiting / 3600} ���"
        in 2..4 -> solution = "${timeVisiting / 3600} ����"
        in 5..20 -> solution = "${timeVisiting / 3600} �����"
    }
    return solution
}

fun agoToDay (timeVisiting:Int):String {
    var solution = ""

    when (timeVisiting / 86400) {
        1 -> solution = "${timeVisiting / 86400} �����"
        in 2..3 -> solution = "${timeVisiting / 86400} �����"
        else -> solution = "�����"
    }
    return solution
}


fun task2 () {
    var typeCard = "VK Pay"
    val previousSumma = 0.0

    println("������� �2. �������� ��������")
    print("������� ����� �������� � ������: ")
    val amount: Float = readLine()!!.toFloat()   //���� ����� ��������

    //������ ������ ���� ��� ����������� ���� ����� = 2 -MIR, 4 -VISA, 5 -Master, 6 -Maestro, 9 -VK Pay???
    print("������� ����� (������ �����) ����� ��� ��������: ")
    val numberCard = readLine()!!.toCharArray()           //���� ������ ����� ��� ����������� ���� �����
    val symbolCard = (numberCard.first())
    when (symbolCard.toString()) {
        "2" -> typeCard = "MIR Card"
        "4" -> typeCard = "VISA Card"
        "5" -> typeCard = "MasterCard"
        "6" -> typeCard = "MaestroCard"
        else -> typeCard = "VK Pay"
    }

    //������ �������� � ����� ��������
    calculationCommission(typeCard, previousSumma, amount)
    println("������ ��������")

}

fun calculationCommission(typeCard: String, previousSumma: Double, amount: Float) {
    val procentKomissMaster = 0.6    // ������� �������� ���� Master
    val summaMinKomissMaster = 20       // ����� �������� Master
    val procentKomissVisaMir = 0.75  // ������� �������� ���� VISA / MIR
    val summaMinKomissVisaMir = 35      // ����� ��� �������� ���� VISA / MIR
    val maxLimitTodayCard = 150_000          // ������������ ����� � ����� �� ������ (����� VK Pay)
    val maxLimitMonthCard = 600_000          // ������������ ����� � ����� �� ������ (����� VK Pay)
    val maxLimitTodayVKPay = 15_000          // ������������ ����� � ����� VK Pay
    val maxLimitMonthVKPay = 40_000          // ������������ ����� � ����� VK Pay
    var summaTransfer = 0.0                  // ����� ��������

    println(typeCard) //������� �� ����� ��� �����

    if (typeCard == "VK Pay") {
        // �������� �� �������� ����� ��� ��������
        if (amount < maxLimitMonthVKPay) {
            // �������� �� �������� ����� ��� ��������
            if (amount < maxLimitTodayVKPay) {
                summaTransfer = amount - previousSumma
                println("����� ��������: $summaTransfer")
            } else {
                println("�������� �������� ����� �� ������� �� ����� VK Pay")
            }
        } else {
            println("�������� �������� ����� �� ������� �� ����� VK Pay")
        }
    }

    if ((amount + previousSumma) < maxLimitMonthCard) {
        if (amount < maxLimitTodayCard) {
            if (typeCard == "MIR Card" || typeCard == "VISA Card") {
                //������ ��������
                if ((amount / 100 * procentKomissVisaMir) < summaMinKomissVisaMir) {
                    summaTransfer = (amount - summaMinKomissVisaMir).toDouble()
                    println("����� ��������: $summaMinKomissVisaMir")
                } else {
                    summaTransfer = amount - (amount / 100 * procentKomissVisaMir)
                    println("����� ��������: " + (amount / 100 * procentKomissVisaMir))
                }
                println("����� ��������: $summaTransfer")
            }

            if (typeCard == "MasterCard" || typeCard == "MaestroCard") {
                summaTransfer = (amount - (amount / 100 * procentKomissMaster)) - summaMinKomissMaster
                println("����� ��������: " + ((amount / 100 * procentKomissMaster) + summaMinKomissMaster))
                println("����� ��������: $summaTransfer")
            }

        } else {
            println("�������� ����� �� ����� �� ����� �������� � �����")
        }

    } else {
            println("�������� ����� �� ����� �� ����� �������� � �����")
    }

}