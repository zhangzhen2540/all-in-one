package org.example;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.example.entity.User;
import org.example.entity.factory.UserFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;

//@SpringBootApplication
public class StartMain {
//    @Value("#{${pingan.copy.all.bind-card.map:{\"SHIPPER\", {\"MAIN\"}, \"OIL_SUPPLIER\": {\"OIL\"}}}}")
//    private Map<PartyType, List<AccountType>> needCopyAllBindCardMap = Map.of(
//            PartyType.SHIPPER, List.of(AccountType.MAIN),
//            PartyType.OIL_SUPPLIER, List.of(AccountType.OIL)
//    );

    @Data
    public static class MyCls {

        @Value("#{${pingan.copy.all.bind-card.map:{\"SHIPPER\": {\"MAIN\"}, \"OIL_SUPPLIER\": {\"OIL\"}}}}")
        private Map<PartyType, List<AccountType>> needCopyAllBindCardMap = Map.of(
                PartyType.SHIPPER, List.of(AccountType.MAIN),
                PartyType.OIL_SUPPLIER, List.of(AccountType.OIL)
        );
    }

    public enum PartyType {
        SHIPPER, OIL_SUPPLIER
    }
    public enum AccountType {
        MAIN, OIL
    }

    public static void main(String[] args) {
//        ApplicationContext app = SpringApplication.run(StartMain.class);
//
//
//
//        app.getBean("");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserFactoryBean.class);
        context.register(MyCls.class);
        context.registerBean("messageSource", ResourceBundleMessageSource.class, () -> messageSource());

        context.refresh();

        MyCls myCls = context.getBean(MyCls.class);
        System.out.println(myCls);

        User bean = context.getBean(User.class);
        System.out.println(JSON.toJSONString(bean));

        System.out.println(context.getEnvironment().getProperty("a"));

        MessageSource messageSource = context.getBean(MessageSource.class);

        String message = messageSource.getMessage("order.broker.apply.transport.pay.limit.error2", new Object[]{80}, LocaleContextHolder.getLocale());
        System.out.println(message);
    }


    public static ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
}