package org.example.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class UserImportBeanDefRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class)
                .addPropertyValue("name", "world")
                .getBeanDefinition();

        System.out.println("容器注入User");
        registry.registerBeanDefinition("user", beanDefinition);
    }

    @Import(UserImportBeanDefRegister.class)
    public static class Start {
        public static void main(String[] args) {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
            ctx.register(Start.class);
            ctx.refresh();

            System.out.println("获取User: " + JSON.toJSONString(ctx.getBean(User.class)));
            System.out.println("获取User: " + JSON.toJSONString(ctx.getBean(User.class)));
        }
    }
}
