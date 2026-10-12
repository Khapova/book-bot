package bookbot.application;

import bookbot.application.ports.QuizStateStorage;
import bookbot.domain.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class AnswerQuestion {
    private final QuizStateStorage storage;
    private final Recommender recommender;
    public AnswerQuestion(QuizStateStorage storage, Recommender recommender) {
        this.storage = storage;
        this.recommender = recommender;
    }
    public QuestionData run(String answer) {
        Quiz quiz = storage.get();
        if (quiz == null) {
            throw new IllegalStateException("Quiz не найден");
        }
        Question question = quiz.getCurrentQuestion();
        quiz.safeAnswer(question, answer);
        storage.save(quiz);
        if (!quiz.isFinished()) {
            Question nextQuestion = quiz.getCurrentQuestion();
            QuestionData questionData = new QuestionData(nextQuestion);
            return questionData;
        }
        else {
            Map<String, String> answers = quiz.getAnswers();
            List<Book> recommendations = recommender.recommend(answers);
            List<BookData> bookDataList = new ArrayList<>();
            for (Book book: recommendations) {
                BookData data = new BookData(book.getTitle(), book.getAuthor());
                bookDataList.add(data);
            }
            RecommendationData recData = new RecommendationData(bookDataList);
            return new RecommendationResults(recData);
        }
    }
}