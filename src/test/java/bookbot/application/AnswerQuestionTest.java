package bookbot.application;

import bookbot.domain.Quiz;
import bookbot.domain.Recommender;
import bookbot.domain.SampleBook;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnswerQuestionTest {
    @Test
    void QuizNotFound() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        assertThrows(IllegalStateException.class, () -> answerQuestion.run("фантастика"));
    }

    @Test
    void AnswerInvalid() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(quiz);
        assertThrows(IllegalArgumentException.class, () -> answerQuestion.run("нет такого варианта ответа"));
    }

    @Test
    void returnNextQuestion() {
        FakeQuizStorage storage = new FakeQuizStorage();
        Recommender recommender = new Recommender(SampleBook.all());
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        Quiz quiz = new Quiz();
        storage.save(quiz);
        AnswerResult result = answerQuestion.run("фантастика");
        assertInstanceOf(NextQuestionResult.class, result);
    }

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

