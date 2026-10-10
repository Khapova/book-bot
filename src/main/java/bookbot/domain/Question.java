package bookbot.domain;

import java.util.List;

public class Question {
	private final String key;
    private final String text;
    private final List<String> options;

    public Question(String key, String text, List<String> options) {
    	this.key = key;
    	this.text = text;
    	this.options = options;

    }
    
    public String getKey() {
    	return key;
    }
    
    public String getText() {
    	return text;
    }
    
    public List<String> getOptions() {
    	return options;
    }
    
    public boolean isValidAnswer(String answer) {
    	return options.contains(answer);
    }
}
