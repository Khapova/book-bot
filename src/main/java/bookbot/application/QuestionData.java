package bookbot.application;

import bookbot.domain.Question;
import java.util.List;

public class QuestionData {
    private final String text;
    private final List<String> options;
    public QuestionData(Question question) {
        this.text = question.getText();
        this.options = question.getOptions();
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }
}
