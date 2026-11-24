package bookbot.application;

import bookbot.domain.Quiz;
import bookbot.domain.Recommender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnswerQuestionTest {
    @Test
    @DisplayName("Если Quiz не найден, бросает исключение")
    void QuizNotFound() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(TestBooks.getSampleBooks());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        assertThrows(IllegalStateException.class, () -> answerQuestion.run(1L,"фантастика"));
    }

    @Test
    @DisplayName("Если пользователь ввел неправильный вариант, бросает исключение")
    void AnswerInvalid() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(TestBooks.getSampleBooks());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(1L, quiz);
        assertThrows(IllegalArgumentException.class, () -> answerQuestion.run(1L, "нет такого варианта ответа"));
    }

    @Test
    @DisplayName("Возвращает нужный класс, если опрос еще не закончен")
    void returnNextQuestion() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(TestBooks.getSampleBooks());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(1L, quiz);
        AnswerResult result = answerQuestion.run(1L, "фантастика");
        assertInstanceOf(NextQuestionResult.class, result);
    }

    @Test
    @DisplayName("Возвращает рекомендацию, если опрос закончен")
    void returnRecommendation() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(TestBooks.getSampleBooks());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(1L, quiz);
        answerQuestion.run(1L, "фантастика");
        answerQuestion.run(1L, "средняя");
        AnswerResult result = answerQuestion.run(1L, "длинная");
        assertInstanceOf(RecommendationResult.class, result);

    }

    @Test
    @DisplayName("Ответы разных пользователей не влияют друг на друга")
    void userDontMixed() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(TestBooks.getSampleBooks());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);

        storage.save(1L, new Quiz());
        storage.save(2L, new Quiz());

        answerQuestion.run(1L, "фантастика");

        assertEquals(1, storage.get(1L).getCurrent_idx());
        assertEquals(0, storage.get(2L).getCurrent_idx());


    }
}

