package ADBproject.LookYourBookUp.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String bibNum;
    private String title;
    private String subjects;
    private String type;
    private List<BookCondition> conditions;
    private List<Review> reviews;

    public Book () {

    }

    public Book(String bibNum, String title, String subjects, String type) {
        this.bibNum = bibNum;
        this.title = title;
        this.subjects = subjects;
        this.type = type;
    }

    public String getBibNum() {
        return bibNum;
    }

    public String getTitle() {
        return title;
    }

    public void setBibNum(String bibNum) {
        this.bibNum = bibNum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public List<BookCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BookCondition> conditions) {
        this.conditions = conditions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
