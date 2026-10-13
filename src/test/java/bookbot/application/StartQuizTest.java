package bookbot.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartQuizTest {
    @Test
            void savesQuiz() {
        FakeQuizStorage storage = new FakeQuizStorage();
        StartQuiz startQuiz = new StartQuiz(storage);
        startQuiz.run();

        assertEquals(1, storage.getSaveCount());
        assertNotNull(storage.get());
    }

    @Test
    void returnFirstQuestion() {
        FakeQuizStorage storage = new FakeQuizStorage();
        StartQuiz startQuiz = new StartQuiz(storage);
        QuestionData result = startQuiz.run();

        assertNotNull(result);
        assertFalse(result.getText().isEmpty());
        assertFalse(result.getOptions().isEmpty());
    }
}

