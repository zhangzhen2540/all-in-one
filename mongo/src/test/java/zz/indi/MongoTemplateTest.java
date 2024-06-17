package zz.indi;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.security.RunAs;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(JUnit4.class)
public class MongoTemplateTest {

    MongoTemplate mongoTemplate;

    @Before
    public void setUP() {
        String databaseName = "finance_prod";
        MongoClient mongoClient = MongoClients.create();
        mongoTemplate = new MongoTemplate(mongoClient, databaseName);
    }

    @Test
    public void testQry() {
        Query qry = new Query().addCriteria(Criteria.where("_id").is(4287033988087809L));
        List<Map> count = mongoTemplate.find(qry, Map.class,"business_statement");
        log.info("resp: {}", count);
    }
    @Test
    public void testQry1() {
        Query qry = new Query();
//        qry.addCriteria(Criteria.where("_id").is(4287033988087809L));

        qry.with(PageRequest.of((int) 0, (int) 2));
        List<Map> count = mongoTemplate.find(qry, Map.class,"business_statement");
        log.info("resp: {}", count);
    }
}
