package bookbot.infrastructure;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.Quiz;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage implements QuizStateStorage {
    private final Map<Long, Quiz> storage = new HashMap<>();

    @Override
    public void save(long userId, Quiz quiz) {
        storage.put(userId, quiz);
    }

    public Quiz get(long userId) {
        return storage.get(userId);
    }

    public void delete(long userId) {
        storage.remove(userId);
    }
}