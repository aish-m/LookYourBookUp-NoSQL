package ADBproject.LookYourBookUp.Models;


public class BookCondition {
    private String barcode;
    private int bookCondition;
    private String userId;

    public BookCondition () {

    }

    public BookCondition(String barcode, int bookCondition, String userId) {
        this.barcode = barcode;
        this.bookCondition = bookCondition;
        this.userId = userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
