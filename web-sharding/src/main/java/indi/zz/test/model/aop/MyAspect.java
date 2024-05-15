package indi.zz.test.model.aop;

import indi.zz.test.model.dao.IPersonDao;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@Aspect
@Order(0)
public class MyAspect {
    @Autowired
    private IPersonDao personDao;

    @Autowired
    ExecutorService defaultThreadPool;

    @Pointcut("@annotation(indi.zz.test.model.aop.TransactionalMessage)")
    public void pointcut() {}

    @After("pointcut()")
    public void after() {

        defaultThreadPool.execute(() -> {
            log.info("total person: {}", personDao.count());
//            boolean save = personDao.save(entity);
//            personDao.list();
//            log.info("dataname: {}", ReadDataSourceRouteManager.getDatasourceName());
//            log.info("my holder val: {}", MyReqHolder.get());
        });

        log.info(">>>>>>>>>> after execute " + TransactionSynchronizationManager.isSynchronizationActive());
    }

    @AfterThrowing(pointcut = "pointcut()")
    public void afterThr() {
        log.info(">>>>>>>>>> after thrown execute");
    }

}
