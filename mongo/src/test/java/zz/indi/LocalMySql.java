package zz.indi;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalMySql {

    @SneakyThrows
    @Test
    public void localMysqlConn() {

        int localPort = 3306;
        String dbName = "logistics";
        try (DruidDataSource druidDs = new DruidDataSource();) {
            druidDs.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            druidDs.setUrl(PropUtil.get("spring.shardingsphere.datasource.slave-ds.url", true));
            druidDs.setUrl("jdbc:mysql://127.0.0.1:" + localPort + "/" + dbName + "?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=GMT%2B8");
            druidDs.setUsername("root");
            druidDs.setPassword("123456");

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

        }


    }
}
