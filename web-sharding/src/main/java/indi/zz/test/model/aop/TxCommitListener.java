//package indi.zz.test.model.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class TxCommitListener implements ApplicationEventPublisherAware {
//    ApplicationEventPublisher applicationEventPublisher;
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//
//
//        applicationEventPublisher.publishEvent(new ApplicationEvent() {
//
//
//
//            @Override
//            public Object getSource() {
//                return super.getSource();
//            }
//        });
//    }
//
//
//    public static class MyEvent extends ApplicationEvent {
//        public MyEvent(Object source) {
//            super(source);
//        }
//    }
//
//}
