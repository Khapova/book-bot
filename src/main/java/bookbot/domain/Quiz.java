package bookbot.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Quiz {
    private List<Question> collection;
    private Map<String, String> answer;
    private int current_idx = 0;
    public Quiz() {
        this.collection = new ArrayList<>();
        this.answer = new HashMap<>();
        this.current_idx = 0;

        collection.add(new Question(
                "genre",
                "Какой жанр книги вам интересен",
                List.of("фантастика", "детектив", "роман")
        ));

        collection.add(new Question(
                "complexity",
                "Насколько сложную книгу вы готовы читать?",
                List.of("сложная", "средняя", "простая")
        ));

        collection.add(new Question(
                "size",
                "Какой объём книги вам удобен",
                List.of("средняя", "длинная", "короткая")
        ));
    }

    public boolean isFinished() {
        return current_idx == collection.size();
    }

    public Question getCurrentQuestion() {
        if (!isFinished()) {
            return collection.get(current_idx);
        }
        return null;
    }

    public void safeAnswer(Question question, String user_answer) {
        answer.put(question.getKey(), user_answer);
        current_idx += 1; //как только записали текущий ответ, можем переходить к следующему

    }

    public Map <String, String> getAnswers() {
        if (isFinished()) {
            return answer;
        }
        return null;
    }
}