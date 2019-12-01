package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ResourceNotFoundException;
import ADBproject.LookYourBookUp.Models.Book;
import ADBproject.LookYourBookUp.Models.BookCondition;
import ADBproject.LookYourBookUp.Models.BookConditionRequestBody;
import ADBproject.LookYourBookUp.Models.ConditionReport;
import ADBproject.LookYourBookUp.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// This controller deals with all the operations related to book condition like getting conditions of all copies of a
// book, inserting condition of a book and running reports to know which books are in which condition

@RestController
@RequestMapping("/api/conditions")
@CrossOrigin(origins = "*")
public class ConditionController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/get/{bibNum}")
    List<BookCondition> getConditionsByBibNum(@PathVariable(value = "bibNum") String bibNum) {
        Book book = bookRepository.findByBibNum(bibNum);
        if(book == null) throw new ResourceNotFoundException("Book", "bibNum", bibNum);
        return book.getConditions();
    }

    @PostMapping("/insert")
    Boolean inputBookCondition(@RequestBody BookCondition requestBody) {
        Book book = bookRepository.findByConditionsBarcode(requestBody.getBarcode());
        if(book == null) throw new ResourceNotFoundException("Book", "barcode", requestBody.getBarcode());

        List<BookCondition> allConditions = book.getConditions();

        for(BookCondition condition : allConditions) {
            if(condition.getBarcode().equals(requestBody.getBarcode())) {
                condition.setBookCondition(requestBody.getBookCondition());
                condition.setUserId(requestBody.getUserId());
                break;
            }
        }
        bookRepository.save(book);

        // Ensure update has happened successfully to appropriate condition field
        Book updatedBook = bookRepository.findByConditionsBarcode(requestBody.getBarcode());
        List<BookCondition> updatedConditions = updatedBook.getConditions();

        for(BookCondition condition : updatedConditions) {
            if(condition.getBarcode().equals(requestBody.getBarcode())) {
                return condition.getUserId().equals(requestBody.getUserId()) &&
                        condition.getBookCondition() == requestBody.getBookCondition();
            }
        }
        return false;
    }

    @RequestMapping("/getReport/{condition}")
    ConditionReport getAllBarcodesForCondition(@PathVariable(value = "condition") int condition) {
        List<BookConditionRequestBody> selectedConditions = new ArrayList<>();
        List<Book> books = bookRepository.findByConditionsBookCondition(condition);

        for(Book book : books) {
            for(BookCondition bookCondition : book.getConditions()) {
                if(bookCondition.getBookCondition() == condition) {
                    BookConditionRequestBody conditionWithBibNum = new BookConditionRequestBody(
                            bookCondition.getBarcode(),
                            book.getBibNum(),
                            bookCondition.getBookCondition(),
                            bookCondition.getUserId()
                    );
                    selectedConditions.add(conditionWithBibNum);
                }
            }
        }
        int countOfBooks = selectedConditions.size();
        return new ConditionReport(selectedConditions, countOfBooks);
    }
}
