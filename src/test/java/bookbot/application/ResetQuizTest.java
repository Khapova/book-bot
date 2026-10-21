package bookbot.application;

import bookbot.domain.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResetQuizTest {
    @Test
    @DisplayName("Проверка удаления квиза при перезапуске")
    void deleteQuiz() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Quiz quiz = new Quiz();
        storage.save(quiz);
        ResetQuiz reset = new ResetQuiz(storage);
        reset.run();
        assertEquals(1, storage.getSaveDelete());
        assertNull(storage.get());
    }
}
