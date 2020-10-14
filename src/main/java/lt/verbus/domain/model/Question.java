package lt.verbus.domain.model;

public class Question {
    int id;
    String text;
    String correctAnswer;
    String allowedAnswerDeviationRange;

    public Question() {
    }

    public Question(int id, String text, String correctAnswer, String allowedAnswerDeviationRange) {
        this.id = id;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.allowedAnswerDeviationRange = allowedAnswerDeviationRange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAllowedAnswerDeviationRange() {
        return allowedAnswerDeviationRange;
    }

    public void setAllowedAnswerDeviationRange(String allowedAnswerDeviationRange) {
        this.allowedAnswerDeviationRange = allowedAnswerDeviationRange;
    }
}
