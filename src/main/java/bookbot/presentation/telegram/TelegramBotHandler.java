package bookbot.presentation.telegram;

import bookbot.application.*;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;

public class TelegramBotHandler {
    private final com.pengrad.telegrambot.TelegramBot bot;
    private final StartQuiz startQuiz;
    private final AnswerQuestion answerQuestion;
    private final ResetQuiz resetQuiz;

    public TelegramBotHandler(String token, StartQuiz startQuiz, AnswerQuestion answerQuestion, ResetQuiz resetQuiz) {
        this.bot = new com.pengrad.telegrambot.TelegramBot(token);
        this.startQuiz = startQuiz;
        this.answerQuestion = answerQuestion;
        this.resetQuiz = resetQuiz;
    }

    public void start() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        System.out.println("Бот запущен и слушает Телеграм");
    }

    private void handleUpdate(Update update) {
        if ((update.message() != null) && (update.message().text() != null)) {
            long userId = update.message().from().id();
            String text = update.message().text();

            if (text.equals("/start")) {
                QuestionData questionData = startQuiz.run(userId);
                sendQuestion(userId, questionData);
            }
            else if (text.equals("/reset")) {
                resetQuiz.run(userId);
                QuestionData questionData = startQuiz.run(userId);
                sendQuestion(userId, questionData);
            }
            else {
                bot.execute(new SendMessage(userId, "Пожалуйста, используйте кнопки для ответа"));
            }
        }
        if (update.callbackQuery() != null) {
            long userId = update.message().from().id();
            String answer = update.callbackQuery().data();

            bot.execute(new AnswerCallbackQuery(update.callbackQuery().id()));

            try {
                AnswerResult result = answerQuestion.run(userId, answer);
                if (result instanceof NextQuestionResult nextQuestionResult) {
                    QuestionData questionData = nextQuestionResult.getNextQuestion();
                    sendQuestion(userId, questionData);
                }
                else if (result instanceof RecommendationResult recommendationResult) {
                    sendRecommendation(userId, recommendationResult);
                }
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                bot.execute(new SendMessage(userId, "Ошибка: " + e.getMessage() + "\nНапишите /start, чтобы начать заново."));
            }
        }

    }

}
