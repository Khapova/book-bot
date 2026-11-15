package bookbot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    @Test
    @DisplayName("Создает книгу")
    void createsBookWithData() {
        Map<String, String> tags = Map.of("genre", "фантастика"); //для создания книги не обязательно, чтобы тэги были правильными
        Book book = Book.tryCreate("Дюна", "Герберт", tags, "");
        assertNotNull(book);
    }

    @Test
    @DisplayName("Возвращает null при нпустом названии")
    void returnNullWhenTitleISEmpty() {
        Map<String, String> tags = Map.of("genre", "фантастика");
        Book book = Book.tryCreate("", "Герберт", tags, "");
        assertNull(book);
    }

    @Test
    @DisplayName("Возвращает null при пустом авторе")
    void returnNullWhenAuthorIEmpty() {
        Map<String, String> tags = Map.of("genre", "фантастика");
        Book book = Book.tryCreate("Дюна", "", tags, "");
        assertNull(book);
    }

    @Test
    @DisplayName("Возвращает null при пустых тэгах")
    void returnNullWhenTagsIsEmpty() {
        Map<String, String> tags = Map.of("genre", "фантастика");
        Book book = Book.tryCreate("Дюна", "Герберт", Map.of(), "");
        assertNull(book);
    }
}
