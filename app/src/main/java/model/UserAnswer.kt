package model

data class UserAnswer(
    val questionIndex: Int = 0, // индекс вопроса в тесте
    val selectedAnswer: Int = -1, // выбранный вариант ответа
    val isCorrect: Boolean = false, // правильный ли ответ
    val timeSpent: Long = 0 // время на ответ в миллисекундах (опционально)
)