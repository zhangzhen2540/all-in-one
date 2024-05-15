package zz.indi.dayi;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import zz.indi.AbnormalCharge;
import zz.indi.AbnormalChargeLog;
import zz.indi.BusinessStatement;
import zz.indi.PropUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TenDataQueryAbnormal {
    static String sshUsername = "";
    static String sshHost = "";
    static String sshPwd = "";
    static Integer sshPort = 22;

    static String remoteHost;
    static int remotePort;
    static int localPort;

    public static void main(String[] args) {
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


//        String databaseName = "dywl";
//        String connectionString = "mongodb://mongouser:x%40hyFU02ea^MvScZ@lb-2sh1lmjx-amwzs3juqyhlkx1c.clb.ap-beijing.tencentclb.com:27017";
//        MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
//
        String databaseName = PropUtil.get("mongo.db.name", true);
        String connectionString = "mongodb://" + PropUtil.get("mongo.db.user", true) + ":" +
                PropUtil.get("mongo.db.pwd", true) + "@" +
                PropUtil.get("mongo.db.host", true) + ":" +
                PropUtil.get("mongo.db.port");
        MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));

        mongoTemplate = new MongoTemplate(mongoClient, databaseName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int bizCnt = 0;
        int bankCnt = 0;
        long lastId = 0L;
        while (true) {
            Query qry = new Query()
                    .addCriteria(Criteria.where(AbnormalCharge.ID).gt(lastId))
//                    .addCriteria(Criteria.where(BusinessStatement.BUSINESS_SERIAL_NO).is("B0-4395523217817602"))
                    .addCriteria(Criteria.where(AbnormalCharge.CREATE_TIME).gt(simpleDateFormat.parse("2020-04-25")));
            qry.with(Sort.by(Sort.Direction.ASC, AbnormalCharge.ID));
            int limit = 1;
            qry.limit(limit);
            List<AbnormalCharge> abList = mongoTemplate.find(qry, AbnormalCharge.class);
            System.out.println(abList);
            break;
//            if (abList.isEmpty()) {
//                break;
//            }
//
//            List<Long> abIds = abList.stream()
//                    .map(AbnormalCharge::getId)
//                    .collect(Collectors.toList());
//
//            Query bankStatmentsQry = new Query()
//                    .addCriteria(Criteria.where(AbnormalChargeLog.ABNORMAL_CHARGE_ID).in(abIds));
//            List<AbnormalChargeLog> abLogList = mongoTemplate.find(bankStatmentsQry, AbnormalChargeLog.class);
//
//            bizCnt += abList.size();
//            bankCnt += abLogList.size();
//            localMongoTemplate.insertAll(abList);
//            localMongoTemplate.insertAll(abLogList);

//            lastId = abList.get(abList.size() - 1).getId();
        }

        log.info("biz cnt: {}, bankCnt: {}", bizCnt, bankCnt);
    }
}
