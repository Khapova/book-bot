package bookbot.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartQuizTest {
    @Test
    @DisplayName("Проверка сохранения квиза")
            void savesQuiz() {
        FakeQuizStorage storage = new FakeQuizStorage();
        StartQuiz startQuiz = new StartQuiz(storage);
        startQuiz.run();

        assertEquals(1, storage.getSaveCount());
        assertNotNull(storage.get());
    }

    @Test
    @DisplayName("Возвращает первый вопрос")
    void returnFirstQuestion() {
        FakeQuizStorage storage = new FakeQuizStorage();
        StartQuiz startQuiz = new StartQuiz(storage);
        QuestionData result = startQuiz.run();

        assertNotNull(result);
        assertFalse(result.getText().isEmpty());
        assertFalse(result.getOptions().isEmpty());
    }
}

