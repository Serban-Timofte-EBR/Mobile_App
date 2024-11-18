package eu.ase.ro.triviachimieorganica.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Result implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(DateConverter.fromDate(this.triviaDate));
        dest.writeInt(this.score);
    }

    protected Result(Parcel in) {
        String date = in.readString();
        this.triviaDate = DateConverter.toDate(date);
        this.score = in.readInt();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
