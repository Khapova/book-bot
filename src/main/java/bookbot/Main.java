package bookbot;

import bookbot.application.*;
import bookbot.domain.Recommender;
import bookbot.domain.SampleBook;
import bookbot.infrastructure.InMemoryStorage;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InMemoryStorage storage = new InMemoryStorage();
        Recommender recommender = new Recommender(SampleBook.all());

        StartQuiz startQuiz = new StartQuiz(storage);
        AnswerQuestion answerQuestion = new AnswerQuestion(storage, recommender);
        ResetQuiz resetQuiz = new ResetQuiz(storage);

        Scanner scanner = new Scanner(System.in);

        QuestionData first = startQuiz.run();
        printQuestion(first);

        while (true) {
            System.out.print("Ваш ответ: ");
            String input = scanner.nextLine().trim();

            if (input.equals("/reset")) {
                resetQuiz.run();
                QuestionData q = startQuiz.run();
                printQuestion(q);
                continue;
            }

            AnswerResult result;
            try {
                result = answerQuestion.run(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            } catch (IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            if (result instanceof NextQuestionResult next) {
                printQuestion(next.getNextQuestion());
            } else if (result instanceof RecommendationResult rec) {
                printBooks(rec);
                System.out.print("Начать заново? (y/n): ");
                String again = scanner.nextLine().trim();
                if (again.equalsIgnoreCase("y")) {
                    resetQuiz.run();
                    QuestionData q = startQuiz.run();
                    printQuestion(q);
                } else {
                    break;
                }
            }
        }

        scanner.close();
    }

    private static void printQuestion(QuestionData q) {
        System.out.println();
        System.out.println(q.getText());
        var options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    private static void printBooks(RecommendationResult rec) {
    	if (rec.getRecommendation().getBooks().isEmpty()) { System.out.println("К сожалению, ничего не нашлось"); return; }
        System.out.println();
        System.out.println("Вот что вам может понравиться:");
        for (BookData b : rec.getRecommendation().getBooks()) {
            System.out.println("• " + b.getTitle() + " — " + b.getAuthor());
        }
    }
}