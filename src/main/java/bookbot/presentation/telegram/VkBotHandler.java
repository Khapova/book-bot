package bookbot.presentation.vk;

import bookbot.application.AnswerQuestion;
import bookbot.application.QuestionData;
import bookbot.application.ResetQuiz;
import bookbot.application.StartQuiz;
import bookbot.application.AnswerResult;
import bookbot.application.NextQuestionResult;
import bookbot.application.RecommendationResult;
import bookbot.application.BookData;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import com.vk.api.sdk.objects.messages.MessageActionStatus;
import com.vk.api.sdk.objects.messages.MessageAction;
import com.vk.api.sdk.objects.messages.responses.GetLongPollServerResponse;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VkBotHandler {

    private final VkApiClient vk;
    private final GroupActor actor;
    private final StartQuiz startQuiz;
    private final AnswerQuestion answerQuestion;
    private final ResetQuiz resetQuiz;

    public VkBotHandler(String accessToken, int groupId, StartQuiz startQuiz, AnswerQuestion answerQuestion, ResetQuiz resetQuiz) {
        this.vk = new VkApiClient(new HttpTransportClient());
        this.actor = new GroupActor(groupId, accessToken);
        this.startQuiz = startQuiz;
        this.answerQuestion = answerQuestion;
        this.resetQuiz = resetQuiz;
    }

    public void start() {
        System.out.println("VK Бот запущен и слушает сообщения!");
        try {
            while (true) {
                List<Message> messages = vk.messages().get(actor)
                        .count(10)
                        .execute().getItems();

                for (Message msg : messages) {
                    handleMessage(msg.getFromId(), msg.getText());
                }

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(long userId, String text) throws Exception {
        if (text.equals("/start")) {
            QuestionData questionData = startQuiz.run(userId);
            sendQuestion(userId, questionData);
        } else if (text.equals("/reset")) {
            resetQuiz.run(userId);
            QuestionData questionData = startQuiz.run(userId);
            sendQuestion(userId, questionData);
        } else {
            try {
                AnswerResult result = answerQuestion.run(userId, text);

                if (result instanceof NextQuestionResult nextQuestionResult) {
                    sendQuestion(userId, nextQuestionResult.getQuestion());
                } else if (result instanceof RecommendationResult recommendationResult) {
                    sendRecommendations(userId, recommendationResult);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                sendMessage(userId, "Ошибка: " + e.getMessage() + "\nНапишите /start");
            }
        }
    }

    private void sendQuestion(long userId, QuestionData questionData) throws Exception {
        // Создаем клавиатуру ВК
        Keyboard keyboard = new Keyboard();
        keyboard.setOneTime(true);
        keyboard.setButtons(questionData.getOptions().stream()
                .map(option -> new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setType(KeyboardButtonActionType.TEXT)
                                .setLabel(option)
                                .setPayload("{\"answer\":\"" + option + "\"}"))
                        .setColor(KeyboardButtonColor.PRIMARY))
                .toList());

        new MessagesSendQuery(vk, actor)
                .message(questionData.getText())
                .peerId(userId)
                .keyboard(keyboard)
                .execute();
    }

    private void sendRecommendations(long userId, RecommendationResult result) throws Exception {
        StringBuilder text = new StringBuilder("Вот что вам может понравиться:\n\n");
        for (BookData book : result.getRecommendation().getBooks()) {
            text.append("📖 ").append(book.getTitle()).append(" — ").append(book.getAuthor()).append("\n\n");
        }
        text.append("Напишите /start, чтобы начать заново");

        new MessagesSendQuery(vk, actor)
                .message(text.toString())
                .peerId(userId)
                .execute();
    }

    private void sendMessage(long userId, String text) throws Exception {
        new MessagesSendQuery(vk, actor)
                .message(text)
                .peerId(userId)
                .execute();
    }
}