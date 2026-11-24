package bookbot.infrastructure;

import bookbot.domain.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryStorageTest {
    @Test
    @DisplayName("Разные Quiz'ы для разных пользователей")
    void savesQDifferentQuizzes() {
        InMemoryStorage storage = new InMemoryStorage();
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();

        storage.save(1L, quiz1);
        storage.save(2L, quiz2);

        assertEquals(quiz1, storage.get(1L));
        assertEquals(quiz2, storage.get(2L));
    }

    @Test
    @DisplayName("Удаляется Quiz правильного пользователя")
    void deleteCorrectQuiz() {
        InMemoryStorage storage = new InMemoryStorage();
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();

        storage.save(1L, quiz1);
        storage.save(2L, quiz2);
        storage.delete(1L);

        assertNull(storage.get(1L));
        assertEquals(quiz2, storage.get(2L));
    }
}