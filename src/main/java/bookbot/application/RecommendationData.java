package bookbot.application;

import java.util.List;

public class RecommendationData {
    private final List<BookData> books;
    public RecommendationData(List<BookData> books) {
        this.books = books;
    }

    public List<BookData> getBooks() {
        return books;
    }
}