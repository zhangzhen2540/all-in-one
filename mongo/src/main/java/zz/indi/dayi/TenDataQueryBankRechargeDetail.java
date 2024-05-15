package zz.indi.dayi;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import zz.indi.BankRechargeDetail;

import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class TenDataQueryBankRechargeDetail {
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

        String databaseName = "dywl";
        String connectionString = "mongodb://mongouser:x%40hyFU02ea^MvScZ@lb-2sh1lmjx-amwzs3juqyhlkx1c.clb.ap-beijing.tencentclb.com:27017";
        MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));

        mongoTemplate = new MongoTemplate(mongoClient, databaseName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int bizCnt = 0;
        int bankCnt = 0;
        long lastId = 0L;
        while (true) {
            Query qry = new Query()
                    .addCriteria(Criteria.where(BankRechargeDetail.ID).gt(lastId))
                    .addCriteria(Criteria.where(BankRechargeDetail.CREATE_TIME)
                            .gt(simpleDateFormat.parse("2024-04-25")));
            qry.with(Sort.by(Sort.Direction.ASC, BankRechargeDetail.ID));
            int limit = 3000;
            qry.limit(limit);
            List<BankRechargeDetail> rechargeList = null;
            try {
                rechargeList = mongoTemplate.find(qry, BankRechargeDetail.class);
                if (rechargeList.isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            bizCnt += rechargeList.size();
            localMongoTemplate.insertAll(rechargeList);

            lastId = rechargeList.get(rechargeList.size() - 1).getId();
        }

        log.info("biz cnt: {}, bankCnt: {}", bizCnt, bankCnt);
    }
}
