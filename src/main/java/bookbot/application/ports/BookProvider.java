package bookbot.application.ports;

import bookbot.domain.Book;
import java.util.List;

public interface BookProvider {
    List<Book> getAllBooks();
}
