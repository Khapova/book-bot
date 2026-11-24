package bookbot.application;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.Quiz;

import java.util.HashMap;
import java.util.Map;

public class FakeQuizStorage implements QuizStateStorage {
    private final Map<Long, Quiz> storage = new HashMap<>();
    private int saveCount = 0;
    private int deleteCount = 0;

    @Override
    public void save(long userId, Quiz quiz) {
        storage.put(userId, quiz);
        saveCount += 1;
    }
    @Override
    public Quiz get(long userId) {
        return storage.get(userId);
    }

    @Override
    public void delete(long userId) {
        storage.remove(userId);
        deleteCount += 1;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public int getSaveDelete() {
        return deleteCount;
    }
}
