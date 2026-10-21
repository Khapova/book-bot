package bookbot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class RecommenderTest {

    @Test
    @DisplayName("Проверка 2 из 3 совпадений")
    void bookWithTwoMatches() {
        Book book = new Book("Дюна", "Герберт",
            Map.of("genre", "фантастика", "size", "длинная", "complexity", "сложная"),
            "Космическая эпопея");
        Recommender rec = new Recommender(List.of(book));

        Map<String, String> answers = Map.of(
            "genre", "фантастика",
            "size", "длинная",
            "complexity", "простая"
        );

        List<Book> result = rec.recommend(answers);

        assertEquals(1, result.size());
        assertEquals("Дюна", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Проверка 1/3 совпадений - пустая рекомендация")
    void bookWithOneMatch() {
        Book book = new Book("Дюна", "Герберт",
            Map.of("genre", "фантастика", "size", "длинная", "complexity", "сложная"),
            "");
        Recommender rec = new Recommender(List.of(book));

        Map<String, String> answers = Map.of(
            "genre", "фантастика",
            "size", "средняя",
            "complexity", "простая"
        );

        List<Book> result = rec.recommend(answers);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Рекомендация строго 3 книги")
    void onlyTopThree() {
        List<Book> books = List.of(
            new Book("К1", "А", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), ""),
            new Book("К2", "А", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), ""),
            new Book("К3", "А", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), ""),
            new Book("К4", "А", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), ""),
            new Book("К5", "А", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), "")
        );
        Recommender rec = new Recommender(books);

        Map<String, String> answers = Map.of(
            "genre", "фантастика",
            "size", "средняя",
            "complexity", "простая"
        );

        List<Book> result = rec.recommend(answers);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Нет совпадений - пустая рекомендация")
    void emptyWhenNoMatch() {
        Book book = new Book("Дюна", "Герберт",
            Map.of("genre", "фантастика", "size", "длинная", "complexity", "сложная"),
            "");
        Recommender rec = new Recommender(List.of(book));

        Map<String, String> answers = Map.of(
            "genre", "детектив",
            "size", "средняя",
            "complexity", "простая"
        );

        List<Book> result = rec.recommend(answers);

        assertTrue(result.isEmpty());
    }
}