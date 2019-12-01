package ADBproject.LookYourBookUp.Models;

import java.util.List;

public class ConditionReport {

    private List<BookConditionRequestBody> bookConditions;
    private int count;

    public ConditionReport(List<BookConditionRequestBody> bookConditions, int count) {
        this.bookConditions = bookConditions;
        this.count = count;
    }

    public List<BookConditionRequestBody> getBookConditions() {
        return bookConditions;
    }

    public void setBookConditions(List<BookConditionRequestBody> bookConditions) {
        this.bookConditions = bookConditions;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
