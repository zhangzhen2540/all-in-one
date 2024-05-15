package org.example;

import org.example.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanLifeCycle implements InitializingBean,
        DisposableBean, ApplicationContextAware {

    @Autowired
    public void setUser(User user) {
        System.out.println("Autowired 执行");
    }

    public BeanLifeCycle() {
        System.out.println("BeanLifeCycle 创建");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("注入 applicationContext");
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct 执行");
    }



    // InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet 执行");
    }


    public void initMethod() {
        System.out.println("@Bean#initMethod执行");
    }


    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy 执行");
    }


    public void destroyMethod() {
        System.out.println("@Bean#destroyMathod 执行");
    }

    // DisposableBean
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.destroy 执行");
    }


    public static class Application {

        public static void main(String[] args) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(Application.class);
            context.refresh();

            context.close();
        }

        @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
        public BeanLifeCycle beanLifeCycle() {
            return new BeanLifeCycle();
        }

        @Bean
        public User user(){
            return new User();
        }
    }
}
