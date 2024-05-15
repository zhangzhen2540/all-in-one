package zz.indi.dayi;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import zz.indi.PropUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MysqlAliQuery {


    public static void main(String[] args) {
        query();
    }

    @SneakyThrows
    private static void query() {

        String db = "adb";
        db = "slave";

        PropUtil.load("ssh-ali.properties");
        PropUtil.load("mysql-ali.properties");
        String sshUser = PropUtil.get("ssh.ali.username", true);
        String sshHost = PropUtil.get("ssh.ali.host", true);
        String sshPwd = PropUtil.get("ssh.ali.password", true);
        String sshPort = PropUtil.get("ssh.ali.port", true);
        String mysqlHost = PropUtil.get("spring.shardingsphere.datasource." + db + "-ds.host", true);
//        sshUser = "zhangzhen";
//        sshPwd = "1jcsxd6@2024";

        System.out.format("ssh user: %s, host: %s, pwd: %s, port: %s, mysqlHost: %s\n",
                sshUser, sshHost, sshPwd, sshPort, mysqlHost);

        int remotePort = 3306;
        int localPort = 33306;
        String dbName = "logistics";
        SshConnection sshConnection = new SshConnection(sshHost, sshUser, sshPwd, Integer.parseInt(sshPort),
                mysqlHost, remotePort, localPort);
        sshConnection.init();

        try (DruidDataSource druidDs = new DruidDataSource();) {
            druidDs.setDriverClassName(PropUtil.get("spring.shardingsphere.datasource.driver-class-name", true));
            druidDs.setUrl("jdbc:mysql://127.0.0.1:" + localPort + "/" + dbName + "?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=GMT%2B8");
            druidDs.setUsername(PropUtil.get("spring.shardingsphere.datasource." + db + "-ds.username", true));
            druidDs.setPassword(PropUtil.get("spring.shardingsphere.datasource." + db + "-ds.password", true));

            druidDs.setInitialSize(1);
            druidDs.setMaxActive(10);
            druidDs.setMaxWait(3000);

            DruidPooledConnection connection = druidDs.getConnection(3000);
            System.out.println(connection);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDs, false);

            jdbcTemplate.query("select * from user_info where id < 100 limit 1", new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    System.out.println("row: " + rs.getString("id"));
                }
            });
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
        } finally {
            sshConnection.destroy();
        }
    }
}
