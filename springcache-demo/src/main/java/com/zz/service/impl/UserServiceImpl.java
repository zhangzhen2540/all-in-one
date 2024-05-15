package com.zz.service.impl;

import com.alibaba.fastjson.JSON;
import com.zz.service.IUserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private Map<Long, User> repo;
    private static long id  = 0;

    @PostConstruct
    public void init() {
        repo = new HashMap<>();
    }

    @Override
    public Long add(User user) {
        if (user.getId() == null) {
            user.setId(id++);
        }
        repo.put(user.getId(), user);
        return user.getId();
    }


    @Override
    @Cacheable(cacheNames = "cache.user", key = "'id='+#id")
    public User get(Long id) {
        return repo.get(id);
    }


    @Cacheable(cacheNames = "abc2", key = "#root.methodName + '[' + #ids + ']'")
    @Override
    public List<User> list(Long... ids) {
        return list(Arrays.asList(ids));
    }

    @Cacheable(cacheNames = "abc", key = "#root.methodName + '[' + #ids + ']'")
    @Override
    public List<User> list(List<Long> ids) {
        return repo.values()
            .stream()
            .filter(u -> ids.contains(u.id))
            .collect(Collectors.toList());
    }

    @Override
    public List<User> listAll() {
        return new ArrayList<>(repo.values());
    }

    @Data
    public static class User {
        private Long id;
        private String name;
        private Integer age;
    }


    @Override
    @Cacheable(cacheNames = "cache.user", key = "'id='+#id")
    public User getById(Long id) {
        log.info(">>>>>>>>> 没走缓存, id=" + id);
        return repo.get(id);
    }

    @Override
    @CacheEvict(key="'id='+#user.id", cacheNames = "cache.user")
    public void updateById(User user) {
        log.info("更新用户: {}", JSON.toJSONString(user));

        repo.put(user.getId(), user);
    }
}
