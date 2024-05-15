package indi.zz.test.model.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dywl.shardingsphere.config.ReadDataSourceRouteManager;
import com.dywl.tm.TmConstants;
import com.dywl.tm.dto.MqTmMsgDTO;
import com.dywl.utils.CollectionUtil;
import com.google.common.collect.Iterables;
import com.rabbitmq.client.Channel;
import indi.zz.test.model.dao.IPersonDao;
import indi.zz.test.model.entity.Person;
import indi.zz.test.model.service.IPersonService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonConsumer {
    @Autowired
    @Qualifier("defaultThreadPool")
    private ExecutorService defaultExecutorService;
    @Autowired
    private IPersonDao personDao;

    @RabbitListener(bindings = {@QueueBinding(
        value = @Queue(name = "person.queue", durable = "true",
            arguments = {
                @Argument(name = "x-dead-letter-exchange", value = TmConstants.TM_DEAD_EXCHANGE),
                @Argument(name = "x-dead-letter-routing-key", value = TmConstants.TM_DEAD_ROUTING_KEY),
                @Argument(name = "x-message-ttl", value = "4000", type = "java.lang.Long")
            }),
        exchange = @Exchange(value = "person.exchange"),
        key = "person.queue"
    )})
    public void invoiceChangeQueue(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("invoiceChangeQueue consumer. msg= {}", msg);
        MqTmMsgDTO mqTmMsgDTO = JSON.parseObject(msg, MqTmMsgDTO.class);

        ReadDataSourceRouteManager.clear();
        ReadDataSourceRouteManager.setWriteReadRoute();
        try {
            List<Person> people = batchInGet(Arrays.asList(mqTmMsgDTO.getContent()),
                subList -> personDao.list(new QueryWrapper<Person>().in(Person.NAME, subList)),
                defaultExecutorService);
//            List<Person> person = personDao.list(new QueryWrapper<Person>().eq(Person.NAME, mqTmMsgDTO.getContent()));
            log.info("query person: {}", JSON.toJSONString(people));

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.info("err", e);
        } finally {
            ReadDataSourceRouteManager.clear();
        }
    }

    public static <T, R> List<R> batchInGet(Collection<T> inSet,
        Function<Collection<T>, Collection<R>> function, ExecutorService defaultExecutorService) {
        if (CollectionUtil.isNullOrEmpty(inSet)) {
            return Collections.emptyList();
        }
        var completableFutures = StreamSupport.stream(
            Iterables.partition(inSet, 2000).spliterator(), true)
            .map(sublist -> CompletableFuture.supplyAsync(
                () -> function.apply(sublist),
                defaultExecutorService))
            .collect(Collectors.toList());
        CompletableFuture.allOf(
            completableFutures.toArray(CompletableFuture[]::new)).join();
        return completableFutures.stream()
            .map(completableFuture -> completableFuture.getNow(
                Collections.emptyList()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

}
