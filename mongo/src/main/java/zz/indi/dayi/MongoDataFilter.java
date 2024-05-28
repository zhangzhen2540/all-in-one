package zz.indi.dayi;

import com.alibaba.fastjson.JSON;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import zz.indi.BusinessStatement;
import zz.indi.PropUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MongoDataFilter {

    private static String localDB = "ten-data";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");



    public static void main(String[] args) {
//        removeDupData();
        checkBizNoDuplicate();
    }

    static MongoTemplate tenMongoTemplate;
    static MongoTemplate localMongoTemplate;

    private static void init() {

        String databaseName = "dywl";
        PropUtil.load("application.properties");
        String mongoUser = PropUtil.get("mongo.db.user", true);
        String mongoPwd = PropUtil.get("mongo.db.pwd", true);
        String mongoHost = PropUtil.get("mongo.db.host", true);
        String connectionString = "mongodb://" + mongoUser + ":" + mongoPwd + "@" + mongoHost + ":27017";
        MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));

        tenMongoTemplate = new MongoTemplate(mongoClient, databaseName);
        localMongoTemplate = new MongoTemplate(MongoClients.create(new ConnectionString("mongodb://localhost")), localDB);
    }


    private static void checkBizNoDuplicate() {
        init();


        Long lastId = 0L;
        while (true) {
            Query query = new Query();
            query.addCriteria(Criteria.where(BusinessStatement.ID).gt(lastId));
            query.addCriteria(Criteria.where(BusinessStatement.TRANSACTION_STATUS).ne(2));
//            query.addCriteria(Criteria.where(BusinessStatement.BUSINESS_TYPE).is(1));
            query.with(Sort.by(BusinessStatement.ID));
            query.limit(3000);

            List<BusinessStatement> bizList = localMongoTemplate.find(query, BusinessStatement.class);
            if (bizList.isEmpty()) {
                break;
            }

            List<String> ids = bizList.stream()
                    .map(BusinessStatement::getPaymentNo)
                    .collect(Collectors.toList());
            Query qry = new Query(Criteria.where(BusinessStatement.PAYMENT_NO).in(ids));
            List<BusinessStatement> dupList = tenMongoTemplate.find(qry, BusinessStatement.class);
            if (!dupList.isEmpty()) {
                log.info("dup list: {}", JSON.toJSONString(dupList));
            }
            lastId = bizList.get(bizList.size() - 1).getId();
        }
        log.info(">>>>>>>>>>>>>>>>>> done");
    }



    private static void removeNoRechargeDup() {

        init();

        Long lastId = 0L;
        while (true) {
            Query query = new Query();
            query.addCriteria(Criteria.where(BusinessStatement.ID).gt(lastId));
            query.addCriteria(Criteria.where(BusinessStatement.BUSINESS_TYPE).ne(1));
            query.limit(3000);

            query.with(Sort.by(BusinessStatement.ID));
            List<BusinessStatement> bizList = localMongoTemplate.find(query, BusinessStatement.class);
            if (bizList.isEmpty()) {
                break;
            }

            List<Long> ids = bizList.stream()
                    .map(BusinessStatement::getId)
                    .collect(Collectors.toList());

            Map<Long, BusinessStatement> bizMap = tenMongoTemplate.find(new Query()
                            .addCriteria(Criteria.where(BusinessStatement.ID).in(ids)), BusinessStatement.class)
                    .stream()
                    .collect(Collectors.toMap(BusinessStatement::getId, a -> a));

            for (BusinessStatement businessStatement : bizList) {
                if (bizMap.containsKey(businessStatement.getId())) {
                    localMongoTemplate.save(businessStatement, "bus_state_dup");
                    localMongoTemplate.save(bizMap.get(businessStatement.getId()), "bus_state_dup2");

                    localMongoTemplate.remove(businessStatement);
                }
            }

            lastId = bizList.get(bizList.size() - 1).getId();
        }
    }

    @SneakyThrows
    private static void removeRechargeDupData() {
        init();

        Long lastId = 0L;
        while (true) {
            Query query = new Query();
            query.addCriteria(Criteria.where(BusinessStatement.ID).gt(lastId));
            query.addCriteria(Criteria.where(BusinessStatement.BUSINESS_TYPE).is(1));
            query.limit(3000);

            List<BusinessStatement> bizList = localMongoTemplate.find(query, BusinessStatement.class);
            if (bizList.isEmpty()) {
                break;
            }

            List<String> ids = bizList.stream()
                    .map(BusinessStatement::getBankSerialNo)
                    .collect(Collectors.toList());

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2024-04-01");
            Map<String, BusinessStatement> bizMap = tenMongoTemplate.find(new Query()
                    .addCriteria(Criteria.where(BusinessStatement.TRANSACTION_TIME).gt(date))
                            .addCriteria(Criteria.where(BusinessStatement.BANK_SERIAL_NO).in(ids)), BusinessStatement.class)
                    .stream()
                    .collect(Collectors.toMap(BusinessStatement::getBankSerialNo, a -> a));

            for (BusinessStatement businessStatement : bizList) {
                if (bizMap.containsKey(businessStatement.getBankSerialNo())) {
                    localMongoTemplate.save(businessStatement, "bus_state_dup_r");
                    localMongoTemplate.save(bizMap.get(businessStatement.getBankSerialNo()), "bus_state_dup_r2");

                    localMongoTemplate.remove(businessStatement);
                }
            }

            lastId = bizList.get(bizList.size() - 1).getId();
        }
    }
}
