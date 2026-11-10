package bookbot.application;

import bookbot.domain.Book;
import java.util.List;
import java.util.Map;

public class TestBooks {
    public static List<Book> getSampleBooks() {
        return List.of(
                new Book("Дюна", "Герберт", Map.of("genre", "фантастика", "size", "длинная", "complexity", "сложная"), ""),
                new Book("Марсианин", "Вейер", Map.of("genre", "фантастика", "size", "средняя", "complexity", "простая"), "")
        );
    }
}