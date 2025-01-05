package pt.isec.a21130067.quizecjetpack.data.model

data class Question(
    val id: String = "",
    val text: String = "",
    val type: String = "",
    val options: List<String> = emptyList()
)
