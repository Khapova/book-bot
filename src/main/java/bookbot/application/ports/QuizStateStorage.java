package bookbot.application.ports;

import bookbot.domain.Quiz;

public interface QuizStateStorage {
    void save(long userId, Quiz quiz);
    Quiz get(long userId);
    void delete(long userId);
}
