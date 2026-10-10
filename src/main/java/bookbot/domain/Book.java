package bookbot.domain;

import java.util.Map;

public class Book {
    private final String title;
    private final String author;
    private final Map<String, String> tags;
    private final String description;

    public Book(String title, String author, Map<String, String> tags, String description) {
        this.title = title;
        this.author = author;
        this.tags = tags;
        this.description = description;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public String getAuthor() {
    	return author;
    }
    
    public Map<String, String> getTags(){
    	return tags;
    }
    
    public String getDescription() {
    	return description;
    }
    
}
