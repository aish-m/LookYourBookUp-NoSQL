package ADBproject.LookYourBookUp.Models;

import javax.validation.constraints.NotNull;

public class ReviewRequestBody {
    @NotNull
    private String bibNum;
    @NotNull
    private String userId;
    @NotNull
    private String reviewHeading;
    @NotNull
    private int reviewRating;
    @NotNull
    private String reviewDescription;
    @NotNull
    private String recommend;

    public ReviewRequestBody() {

    }

    public ReviewRequestBody(int reviewId, String userId, String reviewHeading, int reviewRating, String reviewDescription, String recommend) {
        this.userId = userId;
        this.reviewHeading = reviewHeading;
        this.reviewRating = reviewRating;
        this.reviewDescription = reviewDescription;
        this.recommend = recommend;
    }

    public String getBibNum() {
        return bibNum;
    }

    public void setBibNum(String bibNum) {
        this.bibNum = bibNum;
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
