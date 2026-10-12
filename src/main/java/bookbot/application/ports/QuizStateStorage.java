package bookbot.application.ports;

import bookbot.domain.Quiz;

public interface QuizStateStorage {
    void save(Quiz quiz);
    Quiz get();
    void delete();
}
