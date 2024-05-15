package indi.zz.test.model.service.impl;


import com.alibaba.fastjson.JSON;
import com.dywl.tm.IMQProducerService;
import com.dywl.tm.IProducerTmMessageService;
import com.dywl.tm.aop.TransactionalMessage;
import com.dywl.tm.dto.TmMessageDTO;
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

    @Autowired
    private IProducerTmMessageService messageService;

    @Override
    @TransactionalMessage
    @Transactional(rollbackFor = Exception.class)
    public Long add() {
        Person entity = new Person()
            .setAge(33)
            .setCity("BJ")
            .setName("BBQ" + random.nextInt(99999))
            .setSchool("SH")
            .setStatus(1);

        MyReqHolder.set(entity.getName());
        log.info("before my holder val: {}", MyReqHolder.get());

        personDao.save(entity);

        messageService.add(
            new TmMessageDTO()
                .setExchange("person.exchange")
                .setRoutingKey("person.queue")
                .setContent(entity.getName())
        );

        return Long.valueOf(entity.getId());
    }
}
