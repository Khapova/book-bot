package bookbot.presentation.telegram;

import bookbot.application.*;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton; 

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
                String greeting = "Привет, я бот-рекомендатель!";
                bot.execute(new SendMessage(userId, greeting));
                extracted(userId);
                QuestionData questionData = startQuiz.run(userId);
                sendQuestion(userId, questionData);
            }
            else if (text.equals("/reset")) {
                resetQuiz.run(userId);
                QuestionData questionData = startQuiz.run(userId);
                sendQuestion(userId, questionData);
            }
            else if (text.equals("/help")) {
                extracted(userId);
            }
            else {
                bot.execute(new SendMessage(userId, "Пожалуйста, используйте кнопки для ответа"));
            }
        }
        if (update.callbackQuery() != null) {
            long userId = update.callbackQuery().from().id();
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
    
    
    private void sendQuestion(long userId, QuestionData questionData) {
    	InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    	
    	for (String option : questionData.getOptions()) {
    		InlineKeyboardButton button = new InlineKeyboardButton(option).callbackData(option);
    		keyboard.addRow(button);
    		
    	}
    	SendMessage message = new SendMessage(userId, questionData.getText());
        message.replyMarkup(keyboard);
        bot.execute(message);
    }
    
    private void sendRecommendation(long userId, RecommendationResult result) {
    	StringBuilder text = new StringBuilder("Вот что вам может понравиться:\n\n");
    	
    	for (BookData book : result.getRecommendation().getBooks()) {
    		text.append(book.getTitle()).append(" — ").append(book.getAuthor()).append("\n\n");
    	}
    	text.append("Чтобы начать заново, напишите /start");
    	bot.execute(new SendMessage(userId, text.toString()));
    }

    private void extracted(long userId) {
        String text = "Отвечай на мои вопросы, пиши только те варианты, которые я тебе предложил\n" +
        "Если тебе что-то не понравилось, и ты захотел заново начать опрос, напиши /reset\n" +
        "Если тебе понадобилась помощь, напиши /help\n";
        bot.execute(new SendMessage(userId, text));
    }

}
