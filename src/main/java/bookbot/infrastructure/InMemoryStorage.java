package bookbot.infrastructure;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.Quiz;

public class InMemoryStorage implements QuizStateStorage {
    private Quiz quiz;

    public void save(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz get() {
        return quiz;
    }

    public void delete() {
        quiz = null;
    }
}