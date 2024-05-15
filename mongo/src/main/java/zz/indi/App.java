package zz.indi;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

@Slf4j
public class App {

    protected static MongoTemplate mongoTemplate;
    protected static MongoTemplate localMongoTemplate;
    private static String localDB = "ten-data";


    public static void main(String[] args) {


        String databaseName = "finance_prod";
        MongoClient mongoClient = MongoClients.create();
        mongoTemplate = new MongoTemplate(mongoClient, databaseName);

        Long id = 4287033988087809L;

        Query qry = new Query().addCriteria(Criteria.where("_id").is(id));
        List<BusinessStatement> count = mongoTemplate.find(qry, BusinessStatement.class);
        log.info("resp: {}", JSON.toJSONString(count));

        mongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("id").is(id)),
                new Update().set("companyId", 21L),
                BusinessStatement.class);

        List<BusinessStatement> after = mongoTemplate.find(new Query().addCriteria(Criteria.where("_id").is(id)),
                BusinessStatement.class);
        log.info("resp: {}", JSON.toJSONString(after));

        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                        Criteria.where("transactionMessage").isNull(),
                        Criteria.where("transactionMessage").is("")
                ));
        BusinessStatement xxx = mongoTemplate.findOne(query, BusinessStatement.class);
        log.info("resp>>>>>>>>>>>>>>>>>: {}", JSON.toJSONString(xxx));

        UpdateResult updateResult = mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where("id").is("")),
                new Update().set("xxxx", "bbbb"),
                BusinessStatement.class
        );
        log.info("update: {}, {}", updateResult.getMatchedCount(), updateResult.getModifiedCount());
    }


}
