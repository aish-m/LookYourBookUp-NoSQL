package ADBproject.LookYourBookUp.Models;

public class Review {
    private String userId;
    private String reviewHeading;
    private int reviewRating;
    private String reviewDescription;
    private String recommend;

    public Review () {

    }

    public Review(String userId, String reviewHeading, int reviewRating, String reviewDescription, String recommend) {
        this.userId = userId;
        this.reviewHeading = reviewHeading;
        this.reviewRating = reviewRating;
        this.reviewDescription = reviewDescription;
        this.recommend = recommend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewHeading() {
        return reviewHeading;
    }

    public void setReviewHeading(String reviewHeading) {
        this.reviewHeading = reviewHeading;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
