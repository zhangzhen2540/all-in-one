package indi.zz.test.model.service.impl;

import com.dywl.shardingsphere.config.ReadDataSourceRouteManager;
import indi.zz.test.model.aop.TransactionalMessage;
import indi.zz.test.model.dao.IPersonDao;
import indi.zz.test.model.entity.Person;
import indi.zz.test.model.service.IPersonService;
import indi.zz.test.model.service.MyReqHolder;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDao personDao;

    Random random = new Random() ;

    @Autowired
    ExecutorService defaultThreadPool;

    @Override
    @TransactionalMessage
    @Transactional(rollbackFor = Exception.class)
    public Long add() {
        Person entity = new Person()
            .setAge(33)
            .setCity("BJ")
            .setName("BAA" + random.nextInt(99999))
            .setSchool("SH")
            .setStatus(1);

        MyReqHolder.set(entity.getName());
        log.info("before my holder val: {}", MyReqHolder.get());


        boolean save = personDao.save(entity);
//        ReadDataSourceRouteManager.clear();
//        ReadDataSourceRouteManager.setWriteReadRoute();


//        var completableFutures = StreamSupport.stream(Iterables.partition(Arrays.asList(entity), 10).spliterator(), true)
//            .map(sublist ->
//                CompletableFuture.supplyAsync(
//                    () -> {
//                        log.info("subList: {}", JSON.toJSONString(sublist));
//                        return personDao.list();
//                    },
//                    defaultThreadPool
//                )
//            )
//            .collect(Collectors.toList());
        return 1L;
    }
}
