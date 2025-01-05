package pt.isec.a21130067.quizecjetpack.utils

import com.google.firebase.firestore.FirebaseFirestore
import pt.isec.a21130067.quizecjetpack.data.model.Question
import pt.isec.a21130067.quizecjetpack.data.model.Quiz

object FirestoreManager {
    private val db = FirebaseFirestore.getInstance()

    fun createQuiz(
        title: String,
        description: String,
        onResult: (Boolean, String?, String?) -> Unit
    ) {
        val quizId = generateQuizCode()

        val quiz = mapOf(
            "id" to quizId,
            "title" to title,
            "description" to description
        )

        FirebaseFirestore.getInstance().collection("quizzes").document(quizId)
            .set(quiz)
            .addOnSuccessListener {
                onResult(true, quizId, null)
            }
            .addOnFailureListener { exception ->
                onResult(false, null, exception.message)
            }
    }


    fun generateQuizCode(): String {
        return (100000..999999).random().toString()
    }


    fun saveQuestion(
        quizId: String,
        question: Map<String, Any>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val questionId = FirebaseFirestore.getInstance()
            .collection("quizzes/$quizId/questions")
            .document().id

        FirebaseFirestore.getInstance()
            .collection("quizzes/$quizId/questions")
            .document(questionId)
            .set(question)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { exception -> onResult(false, exception.message) }
    }

    fun getQuizById(quizId: String, onResult: (Quiz?, String?) -> Unit) {
        db.collection("quizzes").document(quizId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val quiz = document.toObject(Quiz::class.java)
                    onResult(quiz, null)
                } else {
                    onResult(null, "Quiz not found")
                }
            }
            .addOnFailureListener { exception ->
                onResult(null, exception.message)
            }
    }

    fun addPlayerToLobby(quizCode: String, playerName: String, onResult: (Boolean, String?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val player = mapOf("name" to playerName)

        db.collection("quizzes")
            .document(quizCode)
            .collection("players")
            .add(player)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { exception -> onResult(false, exception.message) }
    }

}
