package bookbot.domain;

import java.util.Set;

public class Book {
    private final String author;
    private final String title;
    private final Set<String> tags;
    /*добавить описание книги?*/

    public Book(String author, String title, Set<String> tags) {
        this.author = author;
        this.title = title;
        this.tags = tags;
    }
}
