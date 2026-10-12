package bookbot.application;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.Question;
import bookbot.domain.Quiz;

public class StartQuiz {
    private final QuizStateStorage storage;
    public StartQuiz(QuizStateStorage storage) {
        this.storage = storage;
    }

    public QuestionData run() {
        Quiz quiz = new Quiz();
        storage.save(quiz);
        Question currentQuestion = quiz.getCurrentQuestion();
        QuestionData question = new QuestionData(currentQuestion);
        return question;
    }
}
