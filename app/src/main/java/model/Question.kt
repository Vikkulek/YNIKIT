package model

data class Question(
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: Int = -1, // индекс правильного ответа в списке options
    val explanation: String = "" // объяснение ответа (опционально)
)