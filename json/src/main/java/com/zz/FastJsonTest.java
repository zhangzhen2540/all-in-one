package com.zz;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.JSONArray;
import org.json.JSONObject;


public class FastJsonTest {


    public static void main(String[] xxxxx) throws Exception {

        String content = "[{\"baseInfo\":{\"id\":492,\"appId\":\"logistics-gateway\",\"clusterName\":\"default\",\"namespaceName\":\"application\",\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-07T10:52:38.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-07T10:52:38.000+0800\"},\"itemModifiedCnt\":0,\"items\":[],\"format\":\"properties\",\"isPublic\":false,\"parentAppId\":\"logistics-gateway\",\"comment\":\"default app namespace\",\"isConfigHidden\":false},{\"baseInfo\":{\"id\":493,\"appId\":\"logistics-gateway\",\"clusterName\":\"default\",\"namespaceName\":\"application-dev.yml\",\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-07T10:54:27.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-07T10:54:27.000+0800\"},\"itemModifiedCnt\":0,\"items\":[{\"item\":{\"id\":18401,\"namespaceId\":493,\"key\":\"content\",\"value\":\"spring:\\r\\n  cloud:\\r\\n    zookeeper:\\r\\n      connect-string: 172.26.252.71:2181\\r\\n    gateway:\\r\\n      discovery:\\r\\n        locator:\\r\\n          enabled: true\\r\\n          lower-case-service-id: true\\r\\n      routes:\\r\\n        - id: open\\r\\n          uri: http://open.dev.da156.cn\\r\\n          predicates:\\r\\n            - Path\\u003d/dywl/**\\r\\n          filters:\\r\\n            - AddResponseHeader\\u003dX-Response-Foo,Bar\\r\\n            # - StripPrefix\\u003d1\\r\\n        - id: admin\\r\\n          uri: http://houtai.dev.da156.cn\\r\\n          predicates:\\r\\n            - Path\\u003d/admin/**\\r\\n          filters:\\r\\n            - AddResponseHeader\\u003dX-Response-Foo,Bar\\r\\n            - StripPrefix\\u003d1\",\"lineNum\":1,\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-07T10:55:58.000+0800\",\"dataChangeLastModifiedTime\":\"2022-12-26T19:11:16.000+0800\"},\"isModified\":false,\"isDeleted\":false}],\"format\":\"yml\",\"isPublic\":false,\"parentAppId\":\"logistics-gateway\",\"comment\":\"\",\"isConfigHidden\":false},{\"baseInfo\":{\"id\":496,\"appId\":\"logistics-gateway\",\"clusterName\":\"default\",\"namespaceName\":\"application-prod.yml\",\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-21T10:22:03.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-21T10:22:03.000+0800\"},\"itemModifiedCnt\":0,\"items\":[],\"format\":\"yml\",\"isPublic\":false,\"parentAppId\":\"logistics-gateway\",\"comment\":\"\",\"isConfigHidden\":false},{\"baseInfo\":{\"id\":497,\"appId\":\"logistics-gateway\",\"clusterName\":\"default\",\"namespaceName\":\"application-stable.yml\",\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-21T10:22:28.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-21T10:22:28.000+0800\"},\"itemModifiedCnt\":0,\"items\":[{\"item\":{\"id\":18409,\"namespaceId\":497,\"key\":\"content\",\"value\":\"spring:\\r\\n  cloud:\\r\\n    zookeeper:\\r\\n      connect-string: 172.26.252.71:2181\\r\\n    gateway:\\r\\n      discovery:\\r\\n        locator:\\r\\n          enabled: true\\r\\n          lower-case-service-id: true\\r\\n      routes:\\r\\n        - id: open\\r\\n          uri: http://open.stable.da156.cn\\r\\n          predicates:\\r\\n            - Path\\u003d/dywl/**\\r\\n          filters:\\r\\n            - AddResponseHeader\\u003dX-Response-Foo,Bar\\r\\n            # - StripPrefix\\u003d1\\r\\n        - id: admin\\r\\n          uri: http://houtai.stable.da156.cn\\r\\n          predicates:\\r\\n            - Path\\u003d/admin/**\\r\\n          filters:\\r\\n            - AddResponseHeader\\u003dX-Response-Foo,Bar\\r\\n            # - StripPrefix\\u003d1\",\"lineNum\":1,\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-21T10:23:59.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-25T17:44:57.000+0800\"},\"isModified\":false,\"isDeleted\":false}],\"format\":\"yml\",\"isPublic\":false,\"parentAppId\":\"logistics-gateway\",\"comment\":\"\",\"isConfigHidden\":false},{\"baseInfo\":{\"id\":498,\"appId\":\"logistics-gateway\",\"clusterName\":\"default\",\"namespaceName\":\"application-demo.yml\",\"dataChangeCreatedBy\":\"admin\",\"dataChangeLastModifiedBy\":\"admin\",\"dataChangeCreatedTime\":\"2022-11-21T10:22:44.000+0800\",\"dataChangeLastModifiedTime\":\"2022-11-21T10:22:44.000+0800\"},\"itemModifiedCnt\":0,\"items\":[],\"format\":\"yml\",\"isPublic\":false,\"parentAppId\":\"logistics-gateway\",\"comment\":\"\",\"isConfigHidden\":false}]";
        JSONArray arr = new JSONArray(content);
        System.out.println(arr.length());

        for (Object o : arr) {
            StringBuilder sb = new StringBuilder();

            JSONObject jo = (JSONObject) o;

            String parentAppId = jo.getString("parentAppId");

            JSONObject baseInfo = jo.getJSONObject("baseInfo");
            String appId = baseInfo.getString("appId");
            String namespaceName = baseInfo.getString("namespaceName");

            System.out.println("parentId: " + parentAppId + "; appId: " + appId + "; namespaceName: " + namespaceName);
            JSONArray items = jo.getJSONArray("items");
            if (items == null || items.length() == 0) {
                continue;
            }

            for (Object item : items) {
                JSONObject itemObj = (JSONObject) item;


                Boolean isDeleted = itemObj.getBoolean("isDeleted");
                Boolean isModified = itemObj.getBoolean("isModified");

                JSONObject itemDetail = itemObj.getJSONObject("item");
                if (itemDetail == null) {
                    continue;
                }

                String key = itemDetail.getString("key");
                String val = itemDetail.getString("value");

                sb.append(key)
                    .append("=")
                    .append(val)
                    .append("\t")
                    .append("delete=")
                    .append(isDeleted)
                    .append("\t")
                    .append("modify=")
                    .append(isModified)
                    .append("\n");
            }

            String fileName = "/home/zz/Documents/apollo-config/" + parentAppId + "_" + appId + "_" + namespaceName + ".properties";
//            try {
//                Files.write(Paths.get(fileName), sb.toString().getBytes("UTF-8"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
                try {
                    write.write(sb.toString());
                } finally {
                    write.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
