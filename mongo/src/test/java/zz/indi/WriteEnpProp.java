package zz.indi;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class WriteEnpProp {

    // 读本地明文配置文件, 生成加密配置文件
    @Test
    public void writeEnpProp() {
        String encPwd = "";

        try (Scanner scanner = new Scanner("/home/zz/data/dy-config/mongo_enp_pwd")) {
            encPwd = scanner.nextLine();
        }

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(encPwd);

        try (FileInputStream in = new FileInputStream("/home/zz/data/dy-config/mongo.properties")) {
            Properties originProp = new Properties();
            originProp.load(in);

            String encryptUser = encryptor.encrypt(originProp.getProperty("mongo.db.user"));
            String enpPwd = encryptor.encrypt(originProp.getProperty("mongo.db.pwd"));
            String encryptHost = encryptor.encrypt(originProp.getProperty("mongo.db.host"));
            String encryptName = encryptor.encrypt(originProp.getProperty("mongo.db.name"));

            try (FileOutputStream out = new FileOutputStream("src/main/resources/application.properties")) {
                Properties p2 = new Properties();
                p2.setProperty("mongo.db.name", encryptName);
                p2.setProperty("mongo.db.user", encryptUser);
                p2.setProperty("mongo.db.pwd", enpPwd);
                p2.setProperty("mongo.db.host", encryptHost);
                p2.setProperty("mongo.db.port", originProp.getProperty("mongo.db.port"));
                p2.store(out, "init encrypt props");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 读本地明文配置文件, 生成加密配置文件
    @Test
    public void writeMySqlEnpProp() {
        String encPwd = "";

        try (Scanner scanner = new Scanner("/home/zz/data/dy-config/mongo_enp_pwd")) {
            encPwd = scanner.nextLine();
        }

        String[] originProps = new String[]{
            "/home/zz/data/dy-config/mysql-ali.properties",
            "/home/zz/data/dy-config/ssh-ali.properties",
        };

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(encPwd);

        for (String originPropFile : originProps) {
            File file = new File(originPropFile);
            try (FileInputStream in = new FileInputStream(file)) {
                Properties originProp = new Properties();
                originProp.load(in);

                Properties p2 = new Properties();
                for (Map.Entry<Object, Object> entry : originProp.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    System.out.println(key + " : " + value);

                    p2.setProperty(key, encryptor.encrypt(value));
                }

                try (FileOutputStream out = new FileOutputStream("src/main/resources/" + file.getName())) {
                    p2.store(out, "init encrypt props");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
