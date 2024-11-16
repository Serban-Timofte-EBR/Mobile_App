package eu.ase.ro.triviachimieorganica.models;

import java.util.Date;

public class Result {
    private Date triviaDate;
    private Integer score;

    public Result(Date triviaDate, Integer score) {
        this.triviaDate = triviaDate;
        this.score = score;
    }

    public Result() {
    }

    public Date getTriviaDate() {
        return triviaDate;
    }

    public void setTriviaDate(Date triviaDate) {
        this.triviaDate = triviaDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "triviaDate=" + triviaDate +
                ", score=" + score +
                '}';
    }
}
