package ADBproject.LookYourBookUp.Repository;

import ADBproject.LookYourBookUp.Models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    Book findByBibNum(String bibNum);

    Page<Book> findByTitleContaining(String bookTitle, Pageable pageRequest);

    Page<Book> findByType(String bookType, Pageable pageRequest);

    @Query("{'conditions.bookCondition' : {$gte : ?0}}")
    Page<Book> findByConditionsBookConditionGreaterThanQuery (int condition, Pageable pageRequest);

    Page<Book> findByTitleContainingAndType(String bookTitle, String bookType, Pageable pageRequest);

    @Query("{ type : ?0, 'conditions.bookCondition' : {$gte : ?1}}")
    Page<Book> findByTypeAndConditionsBookConditionGreaterThanQuery (String type, int condition, Pageable pageRequest);

    @Query("{ title : { $regex : ?0 }, 'conditions.bookCondition' : { $gte : ?1 } }")
    Page<Book> findByTitleRegexAndConditionsBookConditionGreaterThanQuery (String bookTitle, int condition, Pageable pageRequest);

    @Query("{ title : { $regex : ?0 }, type : ?1, 'conditions.bookCondition' : { $gte : ?2 } }")
    Page<Book> findByTitleRegexAndTypeAndConditionsBookConditionGreaterThanQuery (String bookTitle, String bookType, int condition, Pageable pageRequest);

    Book findByConditionsBarcode(String barcode);

    List<Book> findByConditionsBookCondition(int condition);
}