package model

import java.util.Date

data class TestHistory(
    val id: String = "", // ID записи истории
    val testId: String = "", // ID пройденного теста
    val userId: String = "", // ID пользователя
    val date: Date = Date(), // дата прохождения
    val score: Int = 0, // процент правильных ответов
    val answers: List<UserAnswer> = emptyList() // список ответов
) {
    fun getFormattedDate(): String {
        return android.text.format.DateFormat.format("dd.MM.yyyy HH:mm", date).toString()
    }
}