package com.indi.zz;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AAA {

    @Test
    public void addF() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(1L);
        list.add(0, 222L);
        System.out.println(list);
    }

    @Test
    public void xx() {
//        System.out.println(new ArrayList<Long>(null));
        System.out.println(new ArrayList<Long>(List.of()));
        System.out.println(new ArrayList<Long>(List.of(1L)));
    }

    public static void main(String[] args) {
        BClass bClass = new BClass();
        bClass.setId("b");
        bClass.setName("BBB");
        bClass.setB("BBBBB");

        All all = new All(bClass);
        System.out.println(JSON.toJSONString(all));

        AClass a = new AClass();
        a.setId("a");
        a.setName("aaa");
        a.setA("AAA");
        System.out.println(JSON.toJSONString(new All(a)));

    }


    @Data
    public static class All {
        private String id;
        private String name;
        private String a;
        private String b;

        public All(Base base) {
            System.out.println("base>>>");
            this.id = base.id;
            this.name = base.name;
        }

        public All(AClass a) {
            this((Base) a);
            System.out.println("a>>>");
            this.a = a.a;
        }

        public All(BClass b) {
            this((Base) b);
            System.out.println("b>>>");
            this.b = b.b;
        }
    }

    @Data
    public static class Base {
        private String id;
        private String name;
    }

    @Data
    public static class AClass extends Base {
        private String a;
    }

    @Data
    public static class BClass extends Base {
        private String b;
    }

}
