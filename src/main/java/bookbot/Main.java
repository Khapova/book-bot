package bookbot;

import bookbot.application.*;
import bookbot.application.ports.BookProvider;
import bookbot.domain.Recommender;
import bookbot.infrastructure.InMemoryStorage;
import bookbot.infrastructure.SQLiteBookProvider;
import bookbot.presentation.telegram.TelegramBotHandler;



public class Main {

    public static void main(String[] args) {
    	InMemoryStorage storage = new InMemoryStorage();
        BookProvider bookProvider = new SQLiteBookProvider();
        Recommender recommender = new Recommender(bookProvider.getAllBooks());

        StartQuiz startQuiz = new StartQuiz(storage);
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        ResetQuiz resetQuiz = new ResetQuiz(storage);
        
        String token = "9153336235:AAEW2g3CRH62GxITT3u-vzgj7O20RaqFMJ4";
        TelegramBotHandler botHandler = new TelegramBotHandler(token, startQuiz, answerQuestion, resetQuiz);
        botHandler.start();
    }
}

        

      