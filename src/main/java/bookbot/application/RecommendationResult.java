package bookbot.application;

public class RecommendationResult extends AnswerResult{
    private final RecommendationData recommendation;
    public RecommendationResult(RecommendationData recommendation) {
        this.recommendation = recommendation;
    }
    public RecommendationData getRecommendation() {
        return recommendation;
    }
}
