package zz.indi.dayi;

import com.alibaba.druid.pool.DruidDataSource;
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

    private static void query() {
        PropUtil.load("ssh-ali.properties");
        PropUtil.load("mysql-ali.properties");
        String sshUser = PropUtil.get("ssh.ali.username", true);
        String sshHost = PropUtil.get("ssh.ali.host", true);
        String sshPwd = PropUtil.get("ssh.ali.password", true);
        String sshPort = PropUtil.get("ssh.ali.port", true);

        System.out.format("ssh user: %s, host: %s, pwd: %s, port: %s\n",
                sshUser, sshHost, sshPwd, sshPort);

        String mysqlUrl = PropUtil.get("spring.shardingsphere.datasource.slave-ds.url", true);
        SshConnection sshConnection = new SshConnection(sshHost, sshUser, sshPwd, Integer.parseInt(sshPort),
                mysqlUrl, 3306, 33306);
//        SshConnection sshConnection = new SshConnection();
        sshConnection.init();

        try (DruidDataSource druidDs = new DruidDataSource();) {
            druidDs.setDriverClassName(PropUtil.get("spring.shardingsphere.datasource.slave-ds.type", true));
            druidDs.setUrl(PropUtil.get("spring.shardingsphere.datasource.slave-ds.url", true));
            druidDs.setUsername(PropUtil.get("spring.shardingsphere.datasource.slave-ds.username", true));
            druidDs.setPassword(PropUtil.get("spring.shardingsphere.datasource.slave-ds.password", true));
            druidDs.setInitialSize(1);
            druidDs.setMaxActive(10);
            druidDs.setMaxWait(3000);

            JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDs);

            jdbcTemplate.query("select * from user_info where id < 100 limit 1", new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    System.out.println("row: " + rs.getString("id"));
                }
            });
        } finally {
            sshConnection.destroy();
        }
    }
}
