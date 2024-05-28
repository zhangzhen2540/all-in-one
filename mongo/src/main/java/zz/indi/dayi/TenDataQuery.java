package zz.indi.dayi;

import com.alibaba.fastjson.JSON;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.internal.connection.MessageSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import zz.indi.BankStatement;
import zz.indi.BusinessStatement;
import zz.indi.PropUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TenDataQuery {
    static String sshUsername = "";
    static String sshHost = "";
    static String sshPwd = "";
    static Integer sshPort = 22;

    static String remoteHost;
    static int remotePort;
    static int localPort;

    public static void main(String[] args) {

//        SshConnection sshConnection = new SshConnection(sshHost, sshUsername, sshPwd, sshPort,
//                remoteHost, remotePort, localPort);
//        sshConnection.init();

        try {
            handle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//            sshConnection.destroy();
        }
    }

    protected static MongoTemplate mongoTemplate;
    protected static MongoTemplate localMongoTemplate;
    private static String localDB = "ten-data";


    private static void handle() throws Exception {
        localMongoTemplate = new MongoTemplate(MongoClients.create(new ConnectionString("mongodb://localhost")), localDB);

        String databaseName = "dywl";
        PropUtil.load("application.properties");
        String mongoUser = PropUtil.get("mongo.db.user", true);
        String mongoPwd = PropUtil.get("mongo.db.pwd", true);
        String mongoHost = PropUtil.get("mongo.db.host", true);
        String connectionString = "mongodb://" + mongoUser + ":" + mongoPwd + "@" + mongoHost + ":27017";
        MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));

        mongoTemplate = new MongoTemplate(mongoClient, databaseName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int bizCnt = 0;
        int bankCnt = 0;
        long lastId = 0L;
        while (true) {
            Query qry = new Query()
                    .addCriteria(Criteria.where(BusinessStatement.ID).gt(lastId))
//                    .addCriteria(Criteria.where(BusinessStatement.BUSINESS_SERIAL_NO).is("B0-4395523217817602"))
                    .addCriteria(Criteria.where(BusinessStatement.TRANSACTION_TIME)
                            .gt(simpleDateFormat.parse("2024-04-20")));
            qry.with(Sort.by(Sort.Direction.ASC, BusinessStatement.ID));
            int limit = 3000;
            qry.limit(limit);
            List<BusinessStatement> bizList = mongoTemplate.find(qry, BusinessStatement.class);
            if (bizList.isEmpty()) {
                break;
            }

            List<String> bizNos = bizList.stream()
                    .map(BusinessStatement::getBusinessSerialNo)
                    .collect(Collectors.toList());

            Query bankStatmentsQry = new Query()
                    .addCriteria(Criteria.where(BankStatement.BUSINESS_SERIAL_NO).in(bizNos));
            List<BankStatement> bankStatements = mongoTemplate.find(bankStatmentsQry, BankStatement.class);

            bizCnt += bizList.size();
            bankCnt += bankStatements.size();
            localMongoTemplate.insertAll(bizList);
            localMongoTemplate.insertAll(bankStatements);

            lastId = bizList.get(bizList.size() - 1).getId();
        }

        log.info("biz cnt: {}, bankCnt: {}", bizCnt, bankCnt);
    }
}
