package bookbot.application;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.Quiz;

public class FakeQuizStorage implements QuizStateStorage {
    private Quiz quiz;
    private int saveCount = 0;
    private int deleteCount = 0;
    public void save(Quiz quiz) {
        this.quiz = quiz;
        saveCount += 1;
    }
    public Quiz get() {
        return quiz;
    }
    public void delete() {
        quiz = null;
        deleteCount += 1;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public int getSaveDelete() {
        return deleteCount;
    }
}
