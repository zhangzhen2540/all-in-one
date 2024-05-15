//package zz.indi.dayi;
//
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//@Component
//public class SshContextListener implements ServletContextListener {
//    private SshConnection sshConnection;
//
//    @Override
//    public void contextInitialized(ServletContextEvent arg0) {
//        System.out.println("==========>Context initialized...");
//        try {
//            sshConnection = new SshConnection();
//            sshConnection.init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent arg0) {
//        System.out.println("==========>Context destroyed...");
//        try {
//            sshConnection.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}