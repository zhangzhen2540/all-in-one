package org.example;

import com.alibaba.fastjson.JSON;

public class XXX {

    public static void main(String[] args) {
        MyTest test = new MyTest();
        test.setId(1L);
        System.out.println(JSON.toJSONString(test));
    }
}
