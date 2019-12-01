package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.NoRatingsException;
import ADBproject.LookYourBookUp.Exceptions.ResourceNotFoundException;
import ADBproject.LookYourBookUp.Models.Book;
import ADBproject.LookYourBookUp.Models.Review;
import ADBproject.LookYourBookUp.Models.ReviewRequestBody;
import ADBproject.LookYourBookUp.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This controller has methods for operations on book reviews. This includes getting all reviews for a book and
// inserting reviews for a particular book

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewsController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/get/{bibNum}")
    List<Review> getAllReviewsForBook(@PathVariable(value = "bibNum") String bibNum) {
        return bookRepository.findByBibNum(bibNum).getReviews();
    }

    @RequestMapping("/getRating/{bibNum}")
    Float getRatingForBook(@PathVariable(value = "bibNum") String bibNum) {
        List<Review> reviews =  bookRepository.findByBibNum(bibNum).getReviews();
        float sumOfRatings = 0;

        for(Review review : reviews)  {
            sumOfRatings += review.getReviewRating();
        }
        if(reviews.size() != 0)
            return sumOfRatings/reviews.size();
        else
            throw new NoRatingsException("No ratings yet!");
    }

    @PostMapping("/insert")
    Boolean insertReviewForBook(@RequestBody ReviewRequestBody reviewRequestBody) {
        Book book = bookRepository.findByBibNum(reviewRequestBody.getBibNum());
        if(book == null) throw new ResourceNotFoundException("Book", "bibNum", reviewRequestBody.getBibNum());

        Review newReview = new Review(reviewRequestBody.getUserId(),
                reviewRequestBody.getReviewHeading(),
                reviewRequestBody.getReviewRating(),
                reviewRequestBody.getReviewDescription(),
                reviewRequestBody.getRecommend());

        List<Review> reviews = book.getReviews();
        int initialCount = reviews.size();
        reviews.add(newReview);
        book.setReviews(reviews);
        bookRepository.save(book);

        // Ensure a review has been added to that particular book only
        return bookRepository.findByBibNum(reviewRequestBody.getBibNum()).getReviews().size() == initialCount + 1;
    }
}
