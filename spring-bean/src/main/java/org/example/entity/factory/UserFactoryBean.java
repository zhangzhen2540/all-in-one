package org.example.entity.factory;

import com.alibaba.fastjson.JSON;
import org.example.entity.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserFactoryBean implements FactoryBean<User> {


    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Hello");

        System.out.println("UserFactoryBean 创建 User");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }



    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserFactoryBean.class);

        context.refresh();

        System.out.println("获取User: " + JSON.toJSONString(context.getBean(User.class)));
        System.out.println("获取User: " + JSON.toJSONString(context.getBean(User.class)));
    }
}
