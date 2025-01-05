package pt.isec.a21130067.quizecjetpack.data.model

data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val questions: List<Question> = emptyList()
)