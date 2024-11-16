package eu.ase.ro.triviachimieorganica.models;

public class Question {
    public static final int TYPE_RADIO = 0;
    public static final int TYPE_CHECKBOX = 1;
    public static final int TYPE_SPINNER = 2;
    public static final int TYPE_TEXT = 3;

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int type;
    private String correctAnswer;

    public Question() {
    }

    public Question(String question, String option1, String option2, String option3, int type, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.type = type;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", type='" + type + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
