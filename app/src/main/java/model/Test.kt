package model

data class Test(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val questions: List<Question> = emptyList()
)