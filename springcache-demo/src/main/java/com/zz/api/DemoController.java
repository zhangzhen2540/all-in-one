package com.zz.api;

import com.alibaba.fastjson.JSON;
import com.zz.service.IUserService;
import com.zz.service.UserManager;
import com.zz.service.impl.UserServiceImpl.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserManager userManager;

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("hahaha")
    public String hahaha() {
        User user = userService.get(1L);

        user.setName("xxxxx");
        user.setAge(10000);
        userService.updateById(user);

        return JSON.toJSONString(user);
    }

    @GetMapping("greeting")
    public String greeting() {
        return "hello";
    }

    @CacheEvict(cacheNames = {"user", "demo"}, allEntries = true)
    @PostMapping("/user/add")
    public User addUser(@RequestBody User user) {
        userService.add(user);
        return user;
    }

    @GetMapping("/user/list")
//    @Cacheable(value = "abc", key = "#root.methodName + '[' + #ids + ']'")
    public List<User> list(String ids) {
        List<Long> idList = Stream.of(ids.split(","))
            .map(Long::parseLong)
            .collect(Collectors.toList());
        userService.list(idList.toArray(new Long[]{}));
        return userService.list(idList);
    }

//    @Cacheable(cacheNames = "user", key = "#id")
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @Cacheable(cacheNames = "user", key = "'list'")
    @GetMapping("/user/all")
    public List<User> userAll() {
        return userService.listAll();
    }



    @Cacheable(cacheNames = {"demo", "user"}, key = "'list2'")
    @GetMapping("/user/all2")
    public List<User> userAll2() {
        return userService.listAll();
    }





//
//
//    @Cacheable(cacheNames = "user-2", key = "#id")
//    @GetMapping("/user2/{id}")
//    public User getUser2(@PathVariable Long id) {
//        return userService.get(id);
//    }
}
