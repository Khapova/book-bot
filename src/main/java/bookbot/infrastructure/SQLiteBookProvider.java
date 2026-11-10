package bookbot.infrastructure;

import bookbot.application.ports.BookProvider;
import bookbot.domain.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SQLiteBookProvider implements BookProvider {
    private final String url = "JDBC:sqlite:books.db";

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String selectSQL = "SELECT title, author, genre, size, complexity FROM books";
        try (Connection connection  = DriverManager.getConnection(url);  //закроется автоматически, try-with-resources
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(selectSQL)) {  //executeQuery только для запросов Select
            while (result.next()) {
                String title = result.getString("title");
                String author = result.getString("author");
                Map<String, String> tags = new HashMap<>();
                tags.put("genre", result.getString("genre"));
                tags.put("size", result.getString("size"));
                tags.put("complexity", result.getString("complexity"));

                books.add(new Book(title, author, tags, ""));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Ошибка чтения книг из БД", e);
        }
        return books;
    }
}
