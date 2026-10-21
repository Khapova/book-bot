package bookbot.application;

import bookbot.domain.Quiz;
import bookbot.domain.Recommender;
import bookbot.domain.SampleBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnswerQuestionTest {
    @Test
    @DisplayName("Если Quiz не найден, бросает исключение")
    void QuizNotFound() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        assertThrows(IllegalStateException.class, () -> answerQuestion.run("фантастика"));
    }

    @Test
    @DisplayName("Если пользователь ввел неправильный вариант, бросает исключение")
    void AnswerInvalid() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(quiz);
        assertThrows(IllegalArgumentException.class, () -> answerQuestion.run("нет такого варианта ответа"));
    }

    @Test
    @DisplayName("Возвращает нужный класс, если опрос еще не закончен")
    void returnNextQuestion() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(quiz);
        AnswerResult result = answerQuestion.run("фантастика");
        assertInstanceOf(NextQuestionResult.class, result);
    }

    @Test
    @DisplayName("Возвращает рекомендацию, если опрос закончен")
    void returnRecommendation() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(quiz);
        answerQuestion.run("фантастика");
        answerQuestion.run("средняя");
        AnswerResult result = answerQuestion.run("длинная");
        assertInstanceOf(RecommendationResult.class, result);

    }
}

