package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ResourceNotFoundException;
import ADBproject.LookYourBookUp.Models.Book;
import ADBproject.LookYourBookUp.Models.BookCondition;
import ADBproject.LookYourBookUp.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Books controller to perform all operations related to books like getting books, getting books by their ID and
// filtering books based on various attributes

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{pageNumber}")
    List<Book> getAllBooks(@PathVariable(value = "pageNumber") int pageNumber) {
        return bookRepository.findAll(PageRequest.of(pageNumber-1, 10)).getContent();
    }

    @GetMapping("/getTypes")
    List<String> getPopularBookTypes() {
        return bookRepository.getPopularTypes();
    }

    @GetMapping("/getDetails/{bibNum}")
    Book getBookDetailsByBibNum(@PathVariable(value = "bibNum") String bibNum) {
        Book result = bookRepository.findByBibNum(bibNum);
        if(result == null) throw new ResourceNotFoundException("Book", "bibNum", bibNum);
        return result;
    }

    @GetMapping("/filterBooks")
    List<Book> filterBooks(@RequestParam String bookTitle, @RequestParam String bookType, @RequestParam int bookCondition, @RequestParam int pageNumber) {
        if(!bookTitle.isEmpty() && bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByTitleContaining(bookTitle, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByType(bookType, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && bookType.isEmpty() & bookCondition != 0)
            return bookRepository.findByConditionsBookConditionGreaterThanQuery(bookCondition,
                    PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (!bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByTitleContainingAndType(bookTitle, bookType, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition != 0)
            return bookRepository.findByTypeAndConditionsBookConditionGreaterThanQuery(
                    bookType, bookCondition, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (!bookTitle.isEmpty() && bookType.isEmpty())
            return bookRepository.findByTitleRegexAndConditionsBookConditionGreaterThanQuery(
                    bookTitle, bookCondition, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else
            return bookRepository.findByTitleRegexAndTypeAndConditionsBookConditionGreaterThanQuery(
                    bookTitle, bookType, bookCondition, PageRequest.of(pageNumber-1, 10))
                    .getContent();
    }

    @GetMapping("/getTotalPageCount")
    Long getTotalNumberOfPages() {
        Long countOfBooks = bookRepository.count();
        return (countOfBooks % 10 == 0) ? countOfBooks/10 : countOfBooks/10 + 1;
    }

    @GetMapping("/getTotalBookCount")
    Long getTotalNumberOfBooks() {
        return bookRepository.count();
    }

    @PostMapping("/insert")
    Boolean inputBookCondition(@RequestBody Book book) {
        String newBibNum = book.getBibNum();
        bookRepository.save(book);

        // Ensure book has been inserted into collection
        Book newBook = bookRepository.findByBibNum(newBibNum);
        if(newBook == null) return false;
        return newBook.getTitle().equals(book.getTitle())
                && newBook.getType().equals(book.getTitle())
                && newBook.getSubjects().equals(book.getSubjects())
                && newBook.getConditions().size() == book.getConditions().size()
                && newBook.getReviews() == null;
    }
}
