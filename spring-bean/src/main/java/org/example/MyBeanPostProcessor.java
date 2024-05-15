package org.example;

import org.example.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyBeanPostProcessor {

    public static class UserPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

            if (bean instanceof User) {
                ((User) bean).setName("Fxxxk the world");
            }

            return bean;
        }
    }

    public static class Application {
        public static void main(String[] args) {

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

            context.register(UserPostProcessor.class);
            context.register(User.class);
            context.refresh();

            User bean = context.getBean(User.class);
            System.out.println(bean);

        }
    }

}
