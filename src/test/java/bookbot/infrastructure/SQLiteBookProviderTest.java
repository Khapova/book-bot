package bookbot.infrastructure;

import bookbot.domain.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class SQLiteBookProviderTest {
	@Test
	void shouldLoadBooksFromDatabase() {
		SQLiteBookProvider provider = new SQLiteBookProvider();
		
		List<Book> books = provider.getAllBooks();
		
		assertFalse(books.isEmpty(), "Список книг не должен быть пустым");
		
		Book firstBook = books.get(0);
		
		assertNotNull(firstBook.getTitle(), "У книги должно быть название");
		assertNotNull(firstBook.getAuthor(), "У книги должен быть автор");
	}


}
