package bookbot.application;

public class NextQuestionResult extends AnswerResult{
    private final QuestionData question;
    public NextQuestionResult(QuestionData question) {
        this.question = question;
    }
    public QuestionData getNextQuestion() {
        return question;
    }
}
