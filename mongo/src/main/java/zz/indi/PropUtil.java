package zz.indi;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.data.repository.init.ResourceReader;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PropUtil {

    private static Properties properties;
    static BasicTextEncryptor encryptor;

    static Map<String, Properties> cache = new HashMap<>();


    public static void load(String propName) {
        if (cache.containsKey(propName)) {
            return;
        }
        Properties properties = new Properties();
        try (InputStream in = PropUtil.class.getClassLoader().getResourceAsStream(propName)) {
            properties.load(in);
            cache.put(propName, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String get(String key) {
        return get(key, false);
    }

    public static String get(String key, boolean encrypted) {
        for (Properties props : cache.values()) {
            String property = props.getProperty(key);
            if (property == null) {
                continue;
            }
            if (encrypted) {
                property = decrypt(property);
            }
            return property;
        }
        return null;
    }

    public static String decrypt(String encryptedText) {
        if (encryptor == null) {
            encryptor = new BasicTextEncryptor();
            encryptor.setPassword(encryptorPwd());
        }

        return encryptor.decrypt(encryptedText);
    }


    private static String encryptorPwd() {
        String encPwd = "";
        try (Scanner scanner = new Scanner("/home/zz/data/dy-config/mongo_enp_pwd")) {
            encPwd = scanner.nextLine();
        }

        return encPwd;
    }

}
