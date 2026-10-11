package bookbot.domain;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class Recommender {
	private static final int MIN_MATCH = 2;
	private static final int TOP_LIMIT = 3;
	private final List<Book> books;
	public Recommender(List<Book> books) {
		this.books = books;
	}
	
	
	private int countMatches(Book book, Map<String, String> answers) {
		int count = 0;
		for (Map.Entry<String, String> entry : answers.entrySet()) {
			String tagValue = book.getTags().get(entry.getKey());
			if (entry.getValue().equals(tagValue)) {
				count++;
			}
		}
		return count;
	}
	
	
	public List<Book> recommend(Map<String, String> answers){
		List<Book> result = new ArrayList<>();
		
		for(Book book : books) {
			if(countMatches(book, answers) >= MIN_MATCH) {
				result.add(book);
			}
		}
		
		result.sort((a, b) -> Integer.compare(countMatches(b, answers), countMatches(a, answers)));
		
		
		if(result.size() > TOP_LIMIT) {
			return result.subList(0, TOP_LIMIT);
		}
		return result;
	}
}
