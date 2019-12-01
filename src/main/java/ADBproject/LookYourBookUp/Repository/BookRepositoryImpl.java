package ADBproject.LookYourBookUp.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<String> getPopularTypes() {
        List<String> result = new ArrayList<>();
        GroupOperation groupByTypeAndCount = group("type").count().as("count");
        SortOperation sortByTypeCount = sort(new Sort(Sort.Direction.DESC, "count"));
        LimitOperation limitToTen = limit(10);
        ProjectionOperation projectionOperation = project("type");

        Aggregation aggregation = newAggregation( projectionOperation, groupByTypeAndCount, sortByTypeCount, limitToTen);
        AggregationResults<Map> aggregationResult =
                mongoTemplate.aggregate(aggregation, "books", Map.class);

        for(Map entry : aggregationResult.getMappedResults()) {
            result.add(entry.get("_id").toString());
        }
        return result;
    }
}
