package bookbot.application;

import bookbot.application.ports.QuizStateStorage;

public class ResetQuiz {
    private final QuizStateStorage storage;
    public ResetQuiz(QuizStateStorage storage) {
        this.storage = storage;
    }
    public void run(long userId) {
        storage.delete(userId);
    }
}
