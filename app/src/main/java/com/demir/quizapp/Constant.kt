package com.demir.quizapp

object Constants {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
            1, "What famous does this picture belong to?",
            R.drawable.first,
            "Eminem", "Madonna",
            "Tarkan", "David Bowie", 4
        )

        questionsList.add(que1)

        // 2
        val que2 = Question(
            2, "What club does this flag belong to?",
            R.drawable.second,
            "Menemenspor", "Fenerbahçe",
            "Galatasaray", "Chelsea", 2
        )

        questionsList.add(que2)

        // 3
        val que3 = Question(
            3, "What famous does this picture belong to?",
            R.drawable.third,
            "Kemal Sunal", "İlyas Salman",
            "Şener Şen", "Zeki Alasya", 1
        )

        questionsList.add(que3)

        // 4
        val que4 = Question(
            4, "What country does this flag belong to?",
            R.drawable.fourth,
            "Bahamas", "Panama",
            "Barbados", "Belize", 2
        )

        questionsList.add(que4)

        // 5
        val que5 = Question(
            5, "Who is the architect of the Sultanahmet mosque in the picture?",
            R.drawable.fifth_1,
            "Mimar Sinan", "Mimar Sedefkâr Mehmed Ağa",
            "Sarkis Balyan", "Mimar Kemaleddin", 2
        )

        questionsList.add(que5)

        // 6
        val que6 = Question(
            6, "What famous does this picture belong to?",
            R.drawable.sixth,
            "Orhan Pamuk", "Albert Einstein",
            "Elon Musk", "Stephen Hawking", 4
        )

        questionsList.add(que6)
        return questionsList
    }
}