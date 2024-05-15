package org.example.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class UserImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        System.out.println("userImportSelector selectImports");
        return new String[]{"org.example.entity.User"};
    }


    @Import(UserImportSelector.class)
    public static class Start {
        public static void main(String[] args) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(Start.class);
            context.refresh();

            System.out.println("获取User: " + JSON.toJSONString(context.getBean(User.class)));
            System.out.println("获取User: " + JSON.toJSONString(context.getBean(User.class)));
        }
    }
}
